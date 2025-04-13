import javax.microedition.io.Connector;

import com.nttdocomo.ui.IApplication;
import com.nttdocomo.io.HttpConnection;
import com.nttdocomo.ui.Dialog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class KFM_ResourceManager
{
    final static int REMOTE_BUFFER_COUNT = 2;
    final static int REMOTE_BUFFER_BASE  = 20480;
    final static int REMOTE_BUFFER_MSIZE = 102400;  // 100KB max size for remote buffers

    // A list of resource offset-size pairs.
    static KFM_ResourceItem[] resourceItems;

    /**
     * Initializes the resource system 
    **/
    public static void Initialize()
    {
        try
        {
            // The first int of sp page 0 tells us if resource data is avaliable
            DataInputStream dis = Connector.openDataInputStream("scratchpad:///0;pos=0,length=4");
            int resourcesAvaliable = dis.readInt();
            dis.close();

            // If the value is anything but 1, we want to try to download the resources (lol)
            if (resourcesAvaliable != 1)
            {
                String resourceBaseUri = IApplication.getCurrentApp().getSourceURL();

                // Download remote buffers
                int totalPayloadSize = 0;

                for (int i = 0; i < REMOTE_BUFFER_COUNT; ++i)
                {
                    int payloadSize = DownloadRemoteResource(resourceBaseUri + "res" + i + ".bin", REMOTE_BUFFER_BASE + totalPayloadSize);

                    // If payload size is not more than 0, the download failed.
                    if (payloadSize <= 0)
                        throw new Exception("COULD NOT DOWNLOAD RESOURCES!");
                    else
                        totalPayloadSize += payloadSize;
                }

                // Update our avaliable check field
                DataOutputStream dos = Connector.openDataOutputStream("scratchpad:///0;pos=0,length=4");
                dos.writeInt(1);
                dos.close();
            }

            // Load the resource size table header
            dis = Connector.openDataInputStream("scratchpad:///0;pos=" + REMOTE_BUFFER_BASE + ",length=4");
            int numResources = dis.readInt();
            dis.close();

            // Now we can read the resource table itself
            dis = Connector.openDataInputStream("scratchpad:///0;pos=" + (REMOTE_BUFFER_BASE + 4) + ",length=" + (numResources * 4));
            resourceItems = new KFM_ResourceItem[numResources];

            int resourceOffset = (REMOTE_BUFFER_BASE + 4) + (numResources * 4); // Start at end of table
            int resourceSize   = 0;

            for (int i = 0; i < numResources; ++i)
            {
                // Read the resource size...
                resourceSize = dis.readInt();

                // Create the new resource item...
                resourceItems[i] = new KFM_ResourceItem(resourceOffset, i);

                // Increase resource offset by the size of the current resource
                resourceOffset += resourceSize;
            }

            dis.close();
        } 
        catch (Exception exception)
        {
            // A log message for us...
            System.out.println("FAILED TO INITIALIZE RESOURCES: " + exception.getMessage());

            // A dialog message for the user.
            Dialog errorDialog = new Dialog(2, "ERROR");
            errorDialog.setText(exception.getMessage());
            errorDialog.show();

            // After user confirms dialog, quit.
            IApplication.getCurrentApp().terminate();
        }
    }

    /**
     * Loads a resource from the scratchpad
    **/
    public static byte[] LoadScratchpadResource(int resourceId) throws Exception
    {
        // Bounds check...
        if (resourceId < 0 || resourceId > resourceItems.length)
            throw new Exception("Scratchpad Resource Index Out of Bounds");

        // Get the resource item
        KFM_ResourceItem resourceItem = resourceItems[resourceId];

        // Open up a data stream and read its content
        DataInputStream dis = Connector.openDataInputStream("scratchpad:///0;pos=" + resourceItem.ResourceSPOffset + ",length=" + resourceItem.ResourceSize);

        byte[] resourceBuffer = new byte[resourceItem.ResourceSize];
        dis.readFully(resourceBuffer);
        dis.close();

        return resourceBuffer;
    }

    public static void LoadScratchpadResourceToBuffer(int resourceId, byte[] resourceBuffer) throws Exception
    {
        // Bounds check...
        if (resourceId < 0 || resourceId > resourceItems.length)
            throw new Exception("Scratchpad Resource Index Out of Bounds");

        // Get the resource item
        KFM_ResourceItem resourceItem = resourceItems[resourceId];

        // Open up a data stream and read its content
        DataInputStream dis = Connector.openDataInputStream("scratchpad:///0;pos=" + resourceItem.ResourceSPOffset + ",length=" + resourceBuffer.length);
        dis.readFully(resourceBuffer);
        dis.close();
    }

    /**
     * Connects to a URL and downloads resource data from it into the scratchpad
     * @param resourceUri Resource URI
     * @param spOffset Offset into the SP to write the data
     * @return The size of the resource
     */
    private static int DownloadRemoteResource(String resourceUri, int spOffset)
    {
        OutputStream outputStream = null;
        HttpConnection httpConnection = null;
        InputStream inputStream = null;
        int resourceSize = 0;

        try
        {
            // Connect to the webserver holding our resource...
            httpConnection = (HttpConnection)Connector.open(resourceUri, 1, true);
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            // Open the scratchpad...
            outputStream  = Connector.openOutputStream("scratchpad:///0;pos=" + spOffset);

            // Create an input stream from the connected Uri, read the file into the scratch pad.
            inputStream = httpConnection.openInputStream();

            // Quick loop to read + write bytes from the input intoi our scratch pad.
            int bytesRead = 0;
            byte[] buffer = new byte[REMOTE_BUFFER_MSIZE];  // 100 KB buffer...
            do 
            {
                bytesRead = inputStream.read(buffer);
                outputStream.write(buffer, 0, bytesRead);
                resourceSize += bytesRead;
            } 
            while (bytesRead > 0);
        }
        catch (Exception ex)
        {
            resourceSize = -1;
        }
        finally
        {
            try
            {
                if (inputStream != null)
                    inputStream.close();

                if (httpConnection != null)
                    httpConnection.close();

                if (outputStream != null)
                    outputStream.close();
            } 
            catch (Exception ex) { resourceSize = -1; }
        }

        // Return the resource size _or_ an error code (-1)
        return resourceSize;
    }
}
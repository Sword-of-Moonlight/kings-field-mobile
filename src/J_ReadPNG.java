import java.io.DataInputStream;
import javax.microedition.io.Connector;

public final class J_ReadPNG 
{
    public int m_pix_w = 30;
    public int m_pix_h = 30;
    public int[] m_color;

    public boolean readImage(String paramString, byte paramByte) 
    {
        DataInputStream dataInputStream = null;

        try 
        {
            int i = paramByte % 6;
            int j = paramByte / 6;
            int k = this.m_pix_w * 6;
            dataInputStream = Connector.openDataInputStream("resource:///" + paramString);
            dataInputStream.skipBytes((i * this.m_pix_w + j * k * this.m_pix_h) * 3);
            this.m_color = new int[this.m_pix_w * this.m_pix_h];

            for (byte b = 0; b < this.m_pix_h; b++) 
            {
                for (byte b1 = 0; b1 < this.m_pix_w; b1++) 
                {
                    int m = dataInputStream.readByte() & 0xFF;
                    int n = dataInputStream.readByte() & 0xFF;
                    int i1 = dataInputStream.readByte() & 0xFF;
                    this.m_color[b1 + b * this.m_pix_w] = m << 16 | n << 8 | i1;
                } 

                dataInputStream.skipBytes((k - this.m_pix_w) * 3);
            } 

            dataInputStream.close();
        } 
        catch (Exception exception) 
        {
            try 
            {
                if (dataInputStream != null)
                    dataInputStream.close(); 
            } 
            catch (Exception exception1) 
            {

            }

            dataInputStream = null;
            return false;
        }

        dataInputStream = null;
        return true;
    }
}
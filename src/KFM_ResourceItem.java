/**
 * Stores information about a resource in the table
**/
public class KFM_ResourceItem
{
    public int ResourceSPOffset;
    public int ResourceSize;

    public KFM_ResourceItem(int offset, int size)
    {
        ResourceSPOffset = offset;
        ResourceSize     = size;
    }
}

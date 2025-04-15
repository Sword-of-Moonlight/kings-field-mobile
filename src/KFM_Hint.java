
/** 
 * Used to store a hint. 
**/
public class KFM_Hint 
{
    /**
     * Lines of text which make up the hint. 
    **/
    public final String[] Lines;
    public final int Colour = 0xFFFFFF;

    public KFM_Hint(String[] lines)
    {
        Lines = lines;
    }
}
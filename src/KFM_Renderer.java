import com.nttdocomo.opt.ui.j3d.AffineTrans;
import com.nttdocomo.opt.ui.j3d.Figure;
import com.nttdocomo.opt.ui.j3d.Graphics3D;
import com.nttdocomo.opt.ui.j3d.PrimitiveArray;
import com.nttdocomo.opt.ui.j3d.Texture;

import com.nttdocomo.ui.Font;
import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public final class KFM_Renderer 
{
    static Graphics   graphics2D;
    static Graphics3D graphics3D;
    static Font currentFont = null;

    // Some buffers for various graphics
    static int[] roundRectXBuffer = new int[9];
    static int[] roundRectYBuffer = new int[9];

    // Font Constants
    public static final Font FONT_SMALL   = Font.getFont(0x71000400);
    public static final Font FONT_DEFAULT = Font.getFont(0x71000100);

    // Text Alignment Constants
    public static final int TEXT_HALIGN_CENTER = 0x1;
    public static final int TEXT_VALIGN_CENTER = 0x2;
    public static final int TEXT_HALIGN_LEFT   = 0x8;
    public static final int TEXT_VALIGN_BOTTOM = 0x20;

    // Colour Constants
    public static final int COLOUR_BLACK  = 0xFF000000;
    public static final int COLOUR_WHITE  = 0xFFFFFFFF;
    public static final int COLOUR_LTGREY = 0xFFCCCCCC;
    public static final int COLOUR_RED    = 0xFFCC0000;
    public static final int COLOUR_ORANGE = 0xFFFF6600;
    public static final int COLOUR_YELLOW = 0xFFCCCC00;

    public static void Initialize(Graphics canvasGraphics)
    {
        graphics2D = canvasGraphics;
        graphics3D = (Graphics3D)canvasGraphics;
    }

    public static void Lock()
    {
        graphics2D.lock();
    }

    public static void Unlock()
    {
        graphics2D.unlock(true);
    }

    public static void Flush()
    {
        graphics3D.flush();
    }

    public static Graphics3D GetGraphics3D()
    {
        return graphics3D;
    }

    public static Graphics GetGraphics2D()
    {
        return graphics2D;
    }

    public static void SetViewMatrix(AffineTrans viewMatrix)
    {
        graphics3D.setViewTrans(viewMatrix);
    }

    public static void SetPrimitiveTextureArray(Texture textureArray)
    {
        graphics3D.setPrimitiveTextureArray(textureArray);
    }

    public static void SetPrimitiveTextureIndex(int textureIndex)
    {
        graphics3D.setPrimitiveTexture(textureIndex);
    }

    /**
     * Sets the font used for texture rendering
    **/
    public static void SetFont(Font font)
    {
        graphics2D.setFont(font);
        currentFont = font;
    }

    /**
     * Sets the colour for rendering
    **/
    public static void SetColour(int colour)
    {
        graphics2D.setColor(colour);
    }

    /**
     * Sets the origin for drawing
    **/
    public static void SetOrigin(int xOrigin, int yOrigin)
    {
        graphics2D.setOrigin(xOrigin, yOrigin);
    }

    public static void DrawFilledRect(int x, int y, int w, int h)
    {
        graphics2D.fillRect(x, y, w, h);
    }

    /**
     * Renders an array of primitives inside a PrimitiveArrayObject
    **/
    public static void DrawPrimitiveArray(PrimitiveArray primitives, int n1)
    {
        graphics3D.renderPrimitives(primitives, n1);
    }

    public static void DrawFigure(Figure figure)
    {
        graphics3D.renderFigure(figure);
    }

    /**
     * Draws a rectangle with rounded corners 
    **/
    public static void DrawRoundRect(int x, int y, int w, int h) 
    {
        // X positions for round rect
        roundRectXBuffer[0] = x;
        roundRectXBuffer[1] = x;
        roundRectXBuffer[2] = x + 1;
        roundRectXBuffer[3] = x + w - 1;
        roundRectXBuffer[4] = x + w;
        roundRectXBuffer[5] = x + w;
        roundRectXBuffer[6] = x + w - 1;
        roundRectXBuffer[7] = x + 1;
        roundRectXBuffer[8] = x;

        // Y positions for round rect
        roundRectYBuffer[0] = y + 1;
        roundRectYBuffer[1] = y + h - 1;
        roundRectYBuffer[2] = y + h;
        roundRectYBuffer[3] = y + h;
        roundRectYBuffer[4] = y + h - 1;
        roundRectYBuffer[5] = y + 1;
        roundRectYBuffer[6] = y;
        roundRectYBuffer[7] = y;
        roundRectYBuffer[8] = y + 1;

        graphics2D.drawPolyline(roundRectXBuffer, roundRectYBuffer, 9);
    }

    /**
     * Draws a filled rectangle with rounded corners 
    **/
    public static void DrawFilledRoundRect(int x, int y, int w, int h) 
    {
        // X positions for round rect
        roundRectXBuffer[0] = x;
        roundRectXBuffer[1] = x;
        roundRectXBuffer[2] = x + 1;
        roundRectXBuffer[3] = x + w - 1;
        roundRectXBuffer[4] = x + w;
        roundRectXBuffer[5] = x + w;
        roundRectXBuffer[6] = x + w - 1;
        roundRectXBuffer[7] = x + 1;
        roundRectXBuffer[8] = x;

        // Y positions for round rect
        roundRectYBuffer[0] = y + 1;
        roundRectYBuffer[1] = y + h - 1;
        roundRectYBuffer[2] = y + h;
        roundRectYBuffer[3] = y + h;
        roundRectYBuffer[4] = y + h - 1;
        roundRectYBuffer[5] = y + 1;
        roundRectYBuffer[6] = y;
        roundRectYBuffer[7] = y;
        roundRectYBuffer[8] = y + 1;

        graphics2D.fillPolygon(roundRectXBuffer, roundRectYBuffer, 9);
    }

    /**
     * Draws an image to the screen
    **/
    public static void DrawImage(Image image, int x, int y, int flags) 
    {
        // Horizontal Alignment
        if ((flags & TEXT_HALIGN_CENTER) > 0)
            x -= (image.getWidth() / 2);

        if ((flags & TEXT_HALIGN_LEFT) > 0)
            x -= image.getWidth();

        // Vertical Alignment
        if ((flags & TEXT_VALIGN_CENTER) > 0)
            y -= (image.getHeight() / 2);

        if ((flags & TEXT_VALIGN_BOTTOM) > 0)
            y -= image.getHeight();

        graphics2D.drawImage(image, x, y);
    }

    /**
     * Draws text to the screen 
    **/
    public static void DrawString(String string, int x, int y, int flags)
    {
        // Horizontal Alignment
        if ((flags & TEXT_HALIGN_CENTER) > 0)
            x -= (currentFont.stringWidth(string) / 2);

        if ((flags & TEXT_HALIGN_LEFT) > 0)
            x -= currentFont.stringWidth(string);

        // Vertical Alignment
        y += currentFont.getHeight();   // TO-DO: clean this crap.
        if ((flags & TEXT_VALIGN_CENTER) > 0)
            y -= currentFont.getHeight() / 2;

        if ((flags & TEXT_VALIGN_BOTTOM) > 0)
            y -= currentFont.getHeight();

        // Submit a draw call to the actual canvas
        graphics2D.drawString(string, x, y);
    }
}

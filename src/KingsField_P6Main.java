import com.nttdocomo.ui.Display;
import com.nttdocomo.ui.Frame;
import com.nttdocomo.ui.IApplication;

public class KingsField_P6Main extends IApplication {
  public static KingsField_P6Main app = null;
  
  public static KingsField_P6Canvas canvas = null;
  
  public void start() 
  {
    app = this;
    canvas = new KingsField_P6Canvas();
    Display.setCurrent((Frame)canvas);
    canvas.run();
    IApplication.getCurrentApp().terminate();
  }
  
  public void resume() {}
}
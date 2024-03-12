package graphic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Data {
    public static ArrayList<Drawable> segments;
    protected BufferedImage ball;
    private static Data projectData;
    private Data(){
        segments = new ArrayList<>();
        addImages();
    }
    public static Data projectData(){
        if (projectData == null){
            projectData = new Data();
        }
        return projectData;
    }
    public BufferedImage getBall(){
        return ball;
    }

    private void addImages(){
        try{
            File fileBackground = new File("src/blueball.png");
            ball = ImageIO.read(fileBackground);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addSegment(Drawable segment){
        segments.add(segment);
    }
}

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
//import com.example.mbstress.GlobalListener;
//import com.example.mbstress.GlobalListener.*;


public class ReParseData {
	
	
	private static final String tasks[] = {"CWT", "CWTReverse", "TextEntry", "TextEntryStress"};
	private static final String files[] = {"accel", "accelBoundary", "touch", "touchBoundary", "event", "eventBoundary", "edits",  "editsBoundary"};
	private static final String fsuffix = ".obj";
	private static final String separator = "_";
	private static ObjectInputStream ois;
	private static final String dir = "~/Dropbox/mobistress/data/01/";

	public static void main(String[] args)
	{
			try{
				parse();
			} catch (Exception e){
				e.printStackTrace();
			}
	}
	
	private static void parse() throws FileNotFoundException, ClassNotFoundException
	{
		for(String task : tasks){
			for (String file : files)
			{
				String fileName = dir + task + separator + file + fsuffix;
				try {
					ois = new ObjectInputStream(new FileInputStream(fileName));
					ArrayList data = (ArrayList)ois.readObject();
					System.out.println("Read " + fileName);
					for (Object obj : data) {
						if (obj instanceof String)
						{
							String x = (String)obj;
							System.out.println(x.toString());

						} else {
						System.out.println(obj.toString());
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("error in " + fileName);
					e.printStackTrace();
				}
			}
			
		}
		
		String fileName = dir + "bonus" + fsuffix;
		try {
			ois = new ObjectInputStream(new FileInputStream(fileName));
			ArrayList data = (ArrayList)ois.readObject();

			for (Object obj : data) {
				if (obj instanceof String)
				{
					String x = (String)obj;
					System.out.println(x.toString());

				} else {
				System.out.println(obj.toString());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

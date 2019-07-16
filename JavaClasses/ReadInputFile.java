import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/*
 * This class will be reading input question pool and making a list of the ques 
 */

public class ReadInputFile
{
	public List<Question> readFile() throws Exception
	{
		List<Question> ques=new ArrayList<Question>();
		
		//File file = new File("/home/user/eclipse-workspace/Assignment/src/QuestionsPool12.csv");
		File file = new File("/home/user/eclipse-workspace/Assignment/src/QuestionsPool12.csv");
		Scanner sc = new Scanner(file); 
		String s=sc.nextLine();
		while (sc.hasNextLine()) 
		{			
			s=sc.nextLine();
			String[] words=s.split("\\|"); 
			/*
			 * Setting status of all the ques as NOT_YET_SELECTED
			 */
			Question q=new Question(words[0],Level.valueOf(words[1]),Tag.valueOf(words[2]),Status.NOT_YET_SELECTED);
			ques.add(q);
		 }
		
		for(Question q:ques)
		{
			System.out.print(q);
			System.out.println();
		}
		      
		return ques;
	}
}

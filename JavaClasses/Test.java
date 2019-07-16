import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Test {

	public static void main(String[] args)
	{
		/*
		 * Reading Ques Pool
		 */
		
		ReadInputFile readfile=new ReadInputFile();
		List<Question> qs=new ArrayList<Question>();
		try
		{
			qs=readfile.readFile();
		}
		catch(Exception ex)
		{
			System.err.println("Error while reading the file");
			ex.printStackTrace();	
		}
		/*
		 * Geeting size of quespool
		 */
		
		System.out.println(qs.size());
		
		/*
		 * Generating all maximum number of quizes from pool 
		 * Passing the raw queslist 
		 */
		
		AllQuizGenerator allquiz=new AllQuizGenerator(qs);
		List<Quiz> quizes=allquiz.generateMaxNoQuiz();
		/*
		 * Printing number of quizes generated
		 */
		System.out.println("Total Quiz that can be generated from a given set of questions : "+quizes.size());
		
		/*
		 * Getting number of questions left after generating as many quizes as possible.
		 */
		
		int quesLeft=qs.stream().filter(q -> q.getqStatus()==Status.NOT_YET_SELECTED).collect(Collectors.toList()).size();
		int quesUsed=qs.stream().filter(q -> q.getqStatus()==Status.SELECTED).collect(Collectors.toList()).size();
		
		
		/*
		 * Printing ques left from pool those are not in any quiz 
		 * and the number of ques used in generating quizes.
		 * 
		 */
		System.out.println("Ques Left : "+quesLeft);
		System.out.println("Ques Used : "+quesUsed);

	}

}

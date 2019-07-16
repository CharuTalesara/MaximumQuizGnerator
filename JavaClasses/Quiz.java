import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


/*
 * Quiz --  Contains a list of 10 ques statisfying the following constraints
 * - At least 1 question from each Tag(Constraint 1 )
 * - At least 2 ques from each difficulty level(Constraint 2)
 * - 10 ques ..no more no less (Constraint 3)
 * 
 * For Detailed information each quiz has -
 * 			- Hashtable TagCount  // keeps track of how many ques of each tag has been added 
 * 									to the quiz ques list so far
 *
 * 			- Hashtable  levelCount // keeps track of how many ques of each difficulty level has been 
 * 									added to the quiz so far
 * 
 * 
 * 			As per Constraint 1
 * 				- a tagcount must lie between 1 to 5
 * 			As per constraint 2
 * 				- a level count must lie between 2 to 6
 * 
 * 			This inference helps to check whether a ques is valid to add to the quiz ques list 
 * 
 * 
 * 			The upperlimit constraint has been checked but not the lower limit since the
 * 			 default values has been set as 0
 */

public class Quiz
{
	List<Question> ques; // List of Ques in the quiz ..has to be 10
	Hashtable<Tag,Integer> tagCount;
	Hashtable<Level,Integer> levelCount;

	public Quiz()
	{
		ques=new ArrayList<Question>();
		tagCount=new Hashtable<Tag,Integer>();
		levelCount=new Hashtable<Level,Integer>();
		
		for(Tag tag:Tag.values())
			tagCount.put(tag,0);
		
		for(Level level:Level.values())
			levelCount.put(level,0);
	}
	
	/*
	 * If adding a ques increases count of any tag to 5 or difficulty level count to 6 then that ques is not valid
	 * for this quiz and hence it will be discarded 
	 */
	
	public boolean quesValidity(Question q)
	{
		if(q==null)
			return false;
		
		Tag qtag=q.getQtag();
		Level qlevel=q.getQlevel();
		
		if(tagCount.get(qtag)<5 && levelCount.get(qlevel)<6)
			return true;
		
		return false;
	}
	
	/*
	 * Method to print each question from the quiz question list
	 */
	
	public void printQuizQues()
	{
		for(Question q:ques)
		{
			System.out.println(q);
		}
	}
	
	/*
	 * Method to print TagCount and levelCount Hashtables
	 */
	
	public void printQuizDetails()
	{
		System.out.println(tagCount);
		System.out.println(levelCount);
	}
	/*
	 * Getters and Setters
	 */

	public List<Question> getQues() {
		return ques;
	}

	public void setQues(List<Question> ques) {
		this.ques = ques;
	}

	public Hashtable<Tag, Integer> getTagCount() {
		return tagCount;
	}

	public void setTagCount(Hashtable<Tag, Integer> tagCount) {
		this.tagCount = tagCount;
	}

	public Hashtable<Level, Integer> getLevelCount() {
		return levelCount;
	}

	public void setLevelCount(Hashtable<Level, Integer> levelCount) {
		this.levelCount = levelCount;
	}
	
}

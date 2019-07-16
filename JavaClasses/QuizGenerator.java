import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


/*
 * Generates Quiz from a given set of questions 
 * 
 * every quiz during its generation has 2 matrix 
 * 		1.	pool_matrix -- Contains count of ques from each tag and each difficulty level 
 * 						-- if a particlular que is selected is added to the list of ques of the list then
 * 						count value corrosponding to that very tag and level decreased by 1 
 * 
 * 		2. tag_level_valididity-- keeps a boolean 
 * 							-- true if ques with those tags and level are eligible for adding 
 * 								further to the list else false
 * 
 */
public class QuizGenerator 
{
	Quiz quiz; 
	List<Question> pool;
	int[][] pool_matrix;
	boolean[][] tag_level_valididity;
	
	public QuizGenerator(List<Question> pool,int[][] pool_matrix)
	{
		quiz=new Quiz();
		this.pool=pool;
		this.pool_matrix=pool_matrix;
		tag_level_valididity=new boolean[6][3];
		
		/*
		 * if count in pool_matrix[i][j]==0 => tag_level_valididity is false as ques  for that 
		 * tag and difficulty level are not there or if there adding that will violate the required constraints
		 */
		
		for(int i=0;i<tag_level_valididity.length;i++)
		{
			for(int j=0;j<tag_level_valididity[0].length;j++)
			{
				if(pool_matrix[i][j]!=0)
					tag_level_valididity[i][j]=true;
				else
					tag_level_valididity[i][j]=false;
			}		
		}
	}
	
	public Quiz generateQuiz()
	{
		/*
		 * First for every tag we choose a question  
		 */
		//printBM(tag_level_valididity);
		
		for(Tag tag:Tag.values())
		{
			Question q=quesTagWise(tag);
			
			boolean isVAlidQue= queValidityAndProcessing(q);
			if(!isVAlidQue)
				return null;
			System.out.println(q);
		}
		/*
		 * If after choosing question for every tag .. constraint -2 (i.e. there should be 2 ques for
		 *  each difficulty level)does not fulfills ;
		 *  we choose questions to satisfy that constraint 
		 * 
		 */
		
		while(minLevelCount()!=null)
		{
			Level level=minLevelCount();
			Question q=quesLevelWise(level);
			boolean isVAlidQue= queValidityAndProcessing(q);
			System.out.println(q);
			if(!isVAlidQue)
				return null;
		}
		
		/*
		 * if after satisfying constraint-1 and constraint 2 and the number of ques that has been added 
		 * are not yet 10 then ..we choose ques having max count in the pool_matrix
		 */
		
		while(quiz.ques.size()<10)
		{
			Question q=getQFromMaxPool();
			boolean isVAlidQue= queValidityAndProcessing(q);
			if(!isVAlidQue)
				return null;
			
			//quiz.printQuizDetails();
			System.out.println(q);
			//printMatrix(pool_matrix);
			//printBM(tag_level_valididity);
		}
		
		/*
		 * Added below prints for testing ..
		 */
		
//		quiz.printQuizQues();
//		quiz.printQuizDetails();
//		printMatrix(pool_matrix);		
		return quiz;
	}
	
	/*
	 * Given a tag we choose a level by calling the method getSuitableLevel()
	 * and then choose a ques having given tag and level
	 */
	
	public Question quesTagWise(Tag tag)
	{
		int tagOrdinal=tag.ordinal();
		int levelOrdinal=getSuitableLevel(tagOrdinal);
		
		if(levelOrdinal==-1)
			return null;
		
		List<Question> ques= pool.stream().filter(q -> q.getqStatus()==Status.NOT_YET_SELECTED &&
		(q.getQtag() == tag)  && 
		q.getQlevel()==Level.values()[levelOrdinal]).collect(Collectors.toList());
		
		if(ques.isEmpty())
			return null;
		
		Question q=generateRandomQ(ques);	
		return q;
	}
	
	/*
	 * Checks whether the given question is valid to be aaded to the question list of the quiz 
	 * it further updates the tagCount and levelCount hashtable accordingly
	 * Also,pool_matrix and tag_level_valididity matrix are updated accordingly.
	 * 
	 */
	
	
	public boolean queValidityAndProcessing(Question q)
	{
		if(q==null)
			return false;
		
		int tagOrdinal=q.getQtag().ordinal();
		int levelOrdinal=q.getQlevel().ordinal();
		
		if(quiz.quesValidity(q))
		{
			q.setqStatus(Status.SELECTED);
			quiz.ques.add(q);
			quiz.tagCount.put(q.getQtag(),quiz.tagCount.get(q.getQtag())+1);
			quiz.levelCount.put(q.getQlevel(),quiz.levelCount.get(q.getQlevel())+1);
			pool_matrix[tagOrdinal][levelOrdinal]--;
			if(pool_matrix[tagOrdinal][levelOrdinal]==0)
			{
				tag_level_valididity[tagOrdinal][levelOrdinal]=false;
			}
			pool_matrix[tagOrdinal][3]--;
			pool_matrix[6][levelOrdinal]--;
			pool_matrix[6][3]--;
			
		}
		else
		{
			Level qlevel=q.getQlevel();
			if(quiz.levelCount.get(qlevel)>6)
			{
				for(int i=0;i<6;i++)
					tag_level_valididity[i][qlevel.ordinal()]=false;
			}
			if(quiz.tagCount.get(q.getQtag())>5)
			{
				for(int i=0;i<3;i++)
					tag_level_valididity[q.getQtag().ordinal()][i]=false;
			}
			return false;
		}
		
		return true;
	}
	
	/*
	 * returns the  Difficulty level whose count has not yet reached to 2.
	 */
	
	public Level minLevelCount()
	{
		for(Level level : Level.values())
		{
			if(quiz.levelCount.get(level)<2)
				return level;
		}
		return null;
	}
	
	/*
	 * Given a level ..choose a suitable que
	 */
	public Question quesLevelWise(Level level)
	{
		int levelOrdinal=level.ordinal();
		int tagOrdinal=getSuitableTag(levelOrdinal);
		
		if(tagOrdinal==-1)
			return null;
		
		List<Question> ques= pool.stream().filter(q -> q.getqStatus()==Status.NOT_YET_SELECTED && 
		(q.getQtag() == Tag.values()[tagOrdinal])  && 
		q.getQlevel()==Level.values()[levelOrdinal]).collect(Collectors.toList());
		
		if(ques.isEmpty())
			return null;
		
		Question q=generateRandomQ(ques);
	
		return q;	
	}
	
	/*
	 * Given a tag --get suitable level que to be selected ..ll take the one having max count in pool_matrix so 
	 * that we can have as many valid quizes as possible.
	 */
	
	public int getSuitableLevel(int tagOrdinal)
	{
		if(tagOrdinal<0 || tagOrdinal>5)
			return -1;
		
		int maxLevelCount=-1;
		int levelOrdinal=0;
		
		for(int i=0;i<3;i++)
		{
			if(tag_level_valididity[tagOrdinal][i]==true && maxLevelCount<pool_matrix[tagOrdinal][i])
			{
				maxLevelCount=pool_matrix[tagOrdinal][i];
				levelOrdinal=i;
			}
			
			if(tag_level_valididity[tagOrdinal][i]==true && maxLevelCount==pool_matrix[tagOrdinal][i])
			{
				if(pool_matrix[6][i]>pool_matrix[6][levelOrdinal])
				{
					maxLevelCount=pool_matrix[tagOrdinal][i];
					levelOrdinal=i;
				}
			}
		}
		
		if(maxLevelCount==-1 || maxLevelCount==0)
			return maxLevelCount;
		return levelOrdinal;
	}
	
	/*
	 * For a given level it gets the suitable tag
	 * 
	 */
	
	public int getSuitableTag(int levelOrdinal)
	{
		if(levelOrdinal<0 || levelOrdinal>2)
			return -1;
		
		int maxTagCount=-1;
		int tagOrdinal=0;
		
		for(int i=0;i<6;i++)
		{
			if(tag_level_valididity[i][levelOrdinal]==true && maxTagCount<pool_matrix[i][levelOrdinal])
			{
				maxTagCount=pool_matrix[i][levelOrdinal];
				tagOrdinal=i;
			}
			if(tag_level_valididity[i][levelOrdinal]==true && maxTagCount==pool_matrix[i][levelOrdinal])
			{
				if(pool_matrix[i][3]>pool_matrix[tagOrdinal][3])
				{
					maxTagCount=pool_matrix[i][levelOrdinal];
					tagOrdinal=i;
				}
			}
		}
		
		if(maxTagCount==-1 || maxTagCount==0)
			return maxTagCount;
		
		return tagOrdinal;
	}
	
	/*
	 * Once ques satisfying constraint 1 and constraint 2 are added ..n the ques count of ques is still not 10 then we 
	 * get ques having max value in the pool_matrix
	 */
	
	public Question getQFromMaxPool()
	{
		int[] ordinalDetails=new int[2];
		ordinalDetails[0]=-1;
		ordinalDetails[1]=-1;
		
		int max=-1;
		
		for(int i=0;i<6;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(max<pool_matrix[i][j])
				{
					max=pool_matrix[i][j];
					ordinalDetails[0]=i;
					ordinalDetails[1]=j;
				}
				if(max==pool_matrix[i][j])
				{
					if(pool_matrix[6][j]>pool_matrix[6][ordinalDetails[1]])
					{
						max=pool_matrix[i][j];
						ordinalDetails[0]=i;
						ordinalDetails[1]=j;
					}
					else
					{
						if(pool_matrix[i][3]>pool_matrix[ordinalDetails[0]][3])
						{
							max=pool_matrix[i][j];
							ordinalDetails[0]=i;
							ordinalDetails[1]=j;
						}
					}
				}
			}
		}
		
		List<Question> ques= pool.stream().filter(q -> q.getqStatus()==Status.NOT_YET_SELECTED && 
				(q.getQtag() == Tag.values()[ordinalDetails[0]])  && 
				q.getQlevel()==Level.values()[ordinalDetails[1]]).collect(Collectors.toList());
		

		if(ques.isEmpty())
			return null;
		
		Question q=generateRandomQ(ques);	
		return q;
				
	}

	/*
	 * Checks questions validity --- if on adding a question to the list of ques of the quiz 
	 * tagcount value for the ques's tag gets violated or  levelCount violated then that ques is discarded
	 *  
	 */
	
	public boolean quesValidity(Question q)
	{
		if(q==null)
			return false;
		
		Tag qtag=q.getQtag();
		Level qlevel=q.getQlevel();
		
		if(quiz.tagCount.get(qtag)<5 && quiz.levelCount.get(qlevel)<6)
			return true;
		
		return false;
	}
	
	/*
	 * Given a list of questions -- it chosses any one q randomly.
	 */
	
	public Question generateRandomQ(List<Question> ques)
	{
		 Random random = new Random();
		 int index = random.nextInt(ques.size()); 
		 return ques.get(index);
	}
	
	/*
	 * Getters and setters and printing methods 
	 */
	
	public void printBM(boolean[][] a)
	{
		for(int i=0;i<a.length;i++)
		{
			for(int j=0;j<a[0].length;j++)
				System.out.print(a[i][j]+" ");
			System.out.println();
		}
	}
	
	public boolean[][] getTag_level_valididity() {
		return tag_level_valididity;
	}

	public void setTag_level_valididity(boolean[][] tag_level_valididity) {
		this.tag_level_valididity = tag_level_valididity;
	}
	
	public void printMatrix(int[][] a)
	{
		for(int i=0;i<a.length;i++)
		{
			for(int j=0;j<a[0].length;j++)
				System.out.print(a[i][j]+" ");
			System.out.println();
		}
	}
}
	
	

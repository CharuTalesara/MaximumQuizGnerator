import java.util.List;
import java.util.stream.Collectors;

/*
 * This class checks the question pool that has been passed is processed or not.
 * Provided a list of questions in the constructor it filters that pool by selecting only 
 * those questions whose status is NOT_YET_SELECTED
 * 
 * Also, it creates a check_matrix which keeps count of que from each tag and each difficulty level.
 * 
 * Also, it checks whether a pool is valid(satisfying criteria 1 and 2 and 3) or not
 * 	Criteria 1 -  Atleast 1 Ques From every Tag
 *  Criteria 2 -  Atleast 2 ques from each difficulty level.
 *  Criteria 3- 10 ques in each quiz
 * 
 * this filtered pool and the check_matrix is passed to allQuizGenerator Class to generate as many quiz as possible
 * till the pool is valid
 * 
 */


public class CheckQuesPool 
{
	List<Question> qs;
	int[][] check_matrix;
	
	public CheckQuesPool(List<Question> qs)
	{
		this.qs=qs;
		check_matrix=new int[Tag.values().length+1][Level.values().length+1];
	}
	
	/*
	 * This method gets all the ques which are not yet selected in qny quiz
	 */
	
	public List<Question> filterPool()
	{
		return (qs.stream().filter(q->
		q.getqStatus()==Status.NOT_YET_SELECTED)).collect(Collectors.toList());
		
	}
	
	/*
	 * Counts ques belonging to each tag and within each tag every difficulty level
	 * and updating the matrix for the same
	 * 
	 */
	
	public void updatePoolAndMatrix()
	{
		
		qs=filterPool();
		for(Tag tag:Tag.values())
		{
			for(Level level:Level.values())
			{
				int count =(qs.stream().filter(q-> 
				q.getQtag()==tag && q.getQlevel()==level))
						.collect(Collectors.toList()).size();
				check_matrix[tag.ordinal()][level.ordinal()]=count;
			}
		}
		
		/*
		 * Getting summation of each tag questions in 4th column
		 */
		for(int i=0;i<6;i++)
		{
			int rowendvalue=0;
			for(int j=0;j<3;j++)
				rowendvalue=rowendvalue+check_matrix[i][j];
			check_matrix[i][3]=rowendvalue;
		}
		
		
		/*
		 * Geeting ques sum for each difficulty level in the last row
		 */
		for(int j=0;j<4;j++)
		{
			int colendvalue=0;
			for(int i=0;i<6;i++)
			{
				colendvalue=colendvalue+check_matrix[i][j];
			}
			check_matrix[6][j]=colendvalue;
		}	
		
		/*
		 * Printing the matrix 
		 * For testing purpose only 
		 */
		printMatrix(check_matrix);
	}
	
	/*
	 * Checking pool validity 
	 * if- any difficulty level has less than 2 ques than the pool is invalid as we 
	 * need atleast 2 ques from every difficulty level
	 * 
	 * if - tagcount of any tag is less than 1 then the pool is not valid
	 * 
	 * if - question count is less than 10 then also pool is not valid 
	 */

	public boolean checkPoolValidity()
	{
		updatePoolAndMatrix();
		for(int i=0;i<7;i++)
		{
			if(check_matrix[i][3]<1)
				return false;
		}
		
		for(int j=0;j<3;j++)
		{
			if(check_matrix[6][j]<2)
				return false;
		}
		
		if(check_matrix[6][3]<10)
			return false;
		
		return true;
	}
	
	/*
	 * Printing 2D array 
	 */
	
	public void printMatrix(int[][] a)
	{
		System.out.println();
		for(int i=0;i<a.length;i++)
		{
			for(int j=0;j<a[0].length;j++)
				System.out.print(a[i][j]+" ");
			System.out.println();
		}
	}
	
	/*
	 * Getters and Setters
	 */
	
	public List<Question> getQs() {
		return qs;
	}

	public void setQs(List<Question> qs) {
		this.qs = qs;
	}
	
	public int[][] getCheck_matrix() {
		return check_matrix;
	}

	public void setCheck_matrix(int[][] check_matrix) {
		this.check_matrix = check_matrix;
	}
}



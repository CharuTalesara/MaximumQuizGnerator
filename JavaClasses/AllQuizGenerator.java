import java.util.ArrayList;
import java.util.List;



public class AllQuizGenerator
{
	List<Quiz> quizList;
	CheckQuesPool pool;
	
	/*
	 * Constructor getting raw quesPool 
	 * This will be passed in CheckQuesPool to get the filtered quesList 
	 */
	
	public AllQuizGenerator(List<Question> qs)
	{
		pool=new CheckQuesPool(qs);
		quizList = new ArrayList<Quiz>();
	}
	
	
	/*
	 * Generates the quizes till we have a valid pool of questions 
	 */
	
	public List<Quiz> generateMaxNoQuiz()
	{
		while(pool.checkPoolValidity())
		{
			QuizGenerator quizGenerator=new QuizGenerator(pool.filterPool(),pool.getCheck_matrix());
			Quiz quiz=quizGenerator.generateQuiz();
			if(quiz!=null)
			{
				quizList.add(quiz);
			}
		}	
		
		for(Quiz q:quizList )
		{
			q.printQuizDetails();
			//System.out.println("Quiz length : "+q.ques.size());
		}
		return quizList;
	}
}

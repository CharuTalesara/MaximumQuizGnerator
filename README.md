# MaximumQuizGnerator

The aim here is to generate as many quizes as possible from a pool of questions while satisfying below constraints-

Each question belongs to any of the 6 tags- Tag1,Tag2,Tag3,Tag4,Tag5,Tag6
Each question belings to any one of the difficulty level- EASY,MEDIUM,HARD
A question can be used only once in any quiz.
Each quiz has 10 questions

Example-
	Say from a pool of 600 question, after the first quiz computation, only 590 questions will be available for the next quiz.

Sample Data- 

Question ID  Tag  Difficulty Level 
Sample1     Tag1  HARD
Sample2     Tag5  MEDIUM
Sample3     Tag5  EASY


Criteria To generate each quiz-
1 question from each Tag
2 questions from each difficulty level
10 questions per quiz

Input File-
	Text File with pipe separated fields. 
	Sample input-
	
Question #|Difficulty Level|Tag
Q1|HARD|Tag1
Q2|HARD|Tag2
Q3|MEDIUM|Tag3
Q4|HARD|Tag4
Q5|HARD|Tag5
Q6|MEDIUM|Tag1
Q7|MEDIUM|Tag1
Q8|HARD|Tag2

Output-
Count of quizes that can be generated.

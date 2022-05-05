package com.asbir.cp5307.edugames;

import org.junit.Test;

import static org.junit.Assert.*;

import com.asbir.cp5307.edugames.game.Difficulty;
import com.asbir.cp5307.edugames.game.Game;
import com.asbir.cp5307.edugames.game.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GameUnitTest {
    @Test
    public void checkCorrectAnswer(){
        Question question = createTestQuestion();
        assertTrue(question.check("Answer2"));
    }

    @Test
    public void checkIncorrectAnswer(){
        Question question = createTestQuestion();
        assertFalse(question.check("Answer22323"));
    }

    @Test
    public void checkScore(){
        List<Question> questions = new ArrayList<Question>();
        questions.add(createTestQuestion());
        questions.add(createTestQuestion());
        questions.add(createTestQuestion());
        questions.add(createTestQuestion());
        Game game = new Game(Difficulty.EASY, questions.toArray(new Question[]{}));
        String[] answers = new String[]{"WrongAnswer1", "Answer2", "Answer3", "Answer2"};
        for(int i = 0; i < game.count(); i++){
            Question question = game.next();
            if(question.check(answers[i])){
                game.incrementScore(1);
            }
        }
        assertEquals(2, game.getScore());
    }

    private Question createTestQuestion(){
        Question question = new Question("This is a test question");
        question.addAnswer("Answer1");
        question.addAnswer("Answer2");
        question.addAnswer("Answer3");
        question.setCorrectAnswer("Answer2");
        return question;
    }
}
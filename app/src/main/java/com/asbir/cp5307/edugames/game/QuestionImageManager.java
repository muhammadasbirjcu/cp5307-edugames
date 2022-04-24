package com.asbir.cp5307.edugames.game;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionImageManager extends ImageManager{
    public QuestionImageManager (AssetManager assetManager, String assetPath) {
        super(assetManager, assetPath);
    }

    public QuestionImageManager (AssetManager assetManager) {
        this(assetManager, "questions");
    }

    /**
     * Return a shuffled names where the answer is within the shuffled items
     * @param answer
     * @param limit
     * @return array of names
     * @throws IOException
     */
    public String[] shuffleNames(int answer, int limit) throws IOException {
        List<String> names = Arrays.asList(getNames());
        Collections.shuffle(names);

        // truncate list to first limit-1
        List<String> shuffledNames = names
                .stream()
                .limit(limit-1)
                .collect(Collectors.toList());

        // prepend the correct answer
        shuffledNames.add(0, getName(answer));

        // shuffle again
        Collections.shuffle(shuffledNames);

        return shuffledNames.toArray(new String[0]);
    }

}

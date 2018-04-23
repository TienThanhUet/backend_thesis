package TFIDF;



import com.stadio.model.model.Word;

import java.util.*;

public class TfIdfCalculateImprove {
    int numOfWords;
    double[] idfVector;
    double[][] tfIdfMatrix;
    double[][] tfMatrix;
    String[] wordVector;
    int docLength[];

    public TfIdfCalculateImprove(List<String> docs) {
        docLength = new int[docs.size()];

        HashMap<String, Word> mapWordToIdx = new HashMap<>();// map danh sach cac tu
        LinkedList<String> wordVectorList = new LinkedList();// vector tu
        int nextDocIdx = 0;
        int nextWordIdx = 0;
        for (String doc : docs) {
            String[] words = doc.split(" ");
            docLength[nextDocIdx] = words.length;
            for (String word : words) {
                Word wordObject;
                if (!mapWordToIdx.containsKey(word)) {
                    wordObject = new Word(word,1,nextWordIdx);
                    mapWordToIdx.put(word,wordObject);
                    wordVectorList.add(word);
                    nextWordIdx++;
                }else{
                    wordObject = mapWordToIdx.get(word);
                    wordObject.incrementCountAppear();
                }
                wordObject.addAppearInDoc(nextDocIdx);
            }
            nextDocIdx++;
        }
        ArrayList<Word> wordsList = new ArrayList<Word>(mapWordToIdx.values());

        numOfWords = mapWordToIdx.size();

        wordVector = wordVectorList.toArray(new String[wordVectorList.size()]);

        idfVector = new double[numOfWords];
        for (int wordIdx = 0; wordIdx < wordsList.size(); wordIdx++) {
            Word word = wordsList.get(wordIdx);
            int indexReal  = word.getWordIdx();//check wordidx = indexreal
            idfVector[indexReal] = Math.log10(1 + (double) docs.size() / (word.getAppearDoc().size()));
        }

        tfMatrix = new double[docs.size()][];
        for (int docIdx = 0; docIdx < docs.size(); docIdx++) {
            tfMatrix[docIdx] = new double[numOfWords];
        }

        tfIdfMatrix = new double[docs.size()][];
        for (int docIdx = 0; docIdx < docs.size(); docIdx++) {
            tfIdfMatrix[docIdx] = new double[numOfWords];
        }

        for (int docIdx = 0;docIdx<docs.size();docIdx++) {
            for (int wordIdx = 0; wordIdx < wordsList.size(); wordIdx++) {
                Word word = wordsList.get(wordIdx);
                int indexReal  = word.getWordIdx();
                tfMatrix[docIdx][indexReal] = calculateTf(docIdx,word,docLength[docIdx]);
                tfIdfMatrix[docIdx][indexReal] = tfMatrix[docIdx][indexReal] * idfVector[indexReal];
            }
        }
    }

    public double calculateTf(int docIdx, Word word,int docLength){
        Map<Integer,Integer> appearDoc  = word.getAppearDoc();
        if(appearDoc.containsKey(docIdx)){
            int countAppearInDoc =  appearDoc.get(docIdx);
            return (double)countAppearInDoc*1.0/docLength;
        }else
            return 0;
    }

    public double[][] getTF_IDFMatrix() {
        return tfIdfMatrix;
    }

    public String[] getWordVector() {
        return wordVector;
    }
}

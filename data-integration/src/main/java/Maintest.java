import TFIDF.TfIdfCalculateImprove;
import jvntextpro.JVnTextPro;
import jvntextpro.conversion.CompositeUnicode2Unicode;
import weka.filters.unsupervised.attribute.StringToWordVector;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import scala.Tuple2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maintest {
    public static void main(String[] args) {
        try {
//            List docs = new ArrayList();
//            docs.add("i come from hanoi hanoi is big city capital vietnam");
//            docs.add("u23 was history victory tomorow come back hanoi");
//            docs.add("vnu build at 144 xuanthuy hanoi vnu oldest university vietnam");
//            TfIdfCalculateImprove tfidf = new TfIdfCalculateImprove(docs);
//            double[][] tfidfmaxtrix = tfidf.getTF_IDFMatrix();
//            for(int pos=0;pos<tfidfmaxtrix.length;pos++){
//                for(int pos1=0;pos1<tfidfmaxtrix[pos].length;pos1++){
//                    System.out.print(tfidfmaxtrix[pos][pos1]+"  ");
//                }
//                System.out.println();
//            }

//            Option option = new Option();
//            JVnTextPro vnTextPro = new JVnTextPro();
//            CompositeUnicode2Unicode conversion = new CompositeUnicode2Unicode();
//
//            vnTextPro.initSenTokenization();
//            vnTextPro.initSenSegmenter(option.modelDir.getPath() + File.separator + "jvnsensegmenter");
//            vnTextPro.initSegmenter(option.modelDir.getPath() + File.separator + "jvnsegmenter");
//            vnTextPro.initPosTagger(option.modelDir.getPath() + File.separator + "jvnpostag" + File.separator + "maxent");
//
////            String output = vnTextPro.process(option.inFile);
////            System.out.println(output);
//            new StringToWordVector();
////            Read file, process and write file
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    new FileInputStream(option.inFile), "UTF-8"));
//            String line;
//            StringBuilder ret = new StringBuilder("");
//
//            while ((line = reader.readLine()) != null) {
//                line = conversion.convert(line);
//                ret.append(line);
//            }
//            reader.close();
//            String output = vnTextPro.senSegment(ret.toString());
//            output = vnTextPro.senTokenize(output);
//            output = vnTextPro.wordSegment(output);
//            String[] result= StopWords.removeStopword(output);
//            System.out.println(String.join(",",result));
//
//            //write file
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(option.inFile.getPath() + ".pro"), "UTF-8"));
//            writer.write(ret.toString());
//            writer.close();


            //Create a SparkContext to initialize
            SparkConf conf = new SparkConf().setMaster("local").setAppName("Word Count");

            // Create a Java version of the Spark Context
            JavaSparkContext sc = new JavaSparkContext(conf);

            // Load the text into a Spark RDD, which is a distributed representation of each line of text
            JavaRDD<String> textFile = sc.textFile("/home/sm/Public/thesis/thesis-backend/data-integration/src/main/resources/static/shakespeare.txt");
            JavaPairRDD<String, Integer> counts = textFile
                    .flatMap(s -> Arrays.asList(s.split("[ ,]")).iterator())
                    .mapToPair(word -> new Tuple2<>(word, 1))
                    .reduceByKey((a, b) -> a + b);
            counts.foreach(p -> System.out.println(p));
            System.out.println("Total words: " + counts.count());
            counts.saveAsTextFile("/tmp/shakespeareWordCount");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

class Option {
    File modelDir;
    boolean doSenSeg = true;
    boolean doWordSeg = true;
    boolean doSenToken = true;
    boolean doPosTagging = true;
    File inFile;
    String fileType=".txt";

    public Option(){
            modelDir = new File("/home/sm/Public/thesis/dataIntegration/src/main/resources/models");
            inFile = new File("/home/sm/Public/thesis/article.txt");
    }
}

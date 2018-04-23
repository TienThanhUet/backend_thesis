import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StopWords {
    static String stopWords1[] = {"nhận","rằng", "cao", "nhà", "quá", "riêng", "gì", "muốn", "rồi", "số", "thấy", "hay", "lên", "lần",
            "nào", "qua", "bằng", "điều", "biết", "lớn", "khá", "vừa", "nếu", "thời gian", "họ", "từng", "đây", "tháng", "trước",
            "chính", "cả", "việc", "chưa", "do", "nói", "ra", "nên", "đều", "đi", "tới", "tôi", "có thể", "cùng", "vì", "làm", "lại",
            "mới", "ngày", "đó", "vẫn", "mình", "chỉ", "thì", "đang", "còn", "bị", "mà", "năm", "nhất", "hơn", "sau", "ông", "rất",
            "anh", "phải", "như", "trên", "tại", "theo", "khi", "nhưng", "vào", "đến", "nhiều", "người", "từ", "sẽ", "ở", "cũng",
            "không", "về","để","này","những","một","các","cho","được","với","có","trong","đã","là","và","của","thực_sự","ở_trên","tất_cả",
            "dưới", "hầu_hết","luôn","giữa", "bất","kỳ","hỏi","bạn","cô","tôi","tớ","cậu","bác","chú","dì","thím","cậu","mợ","ông","bà",
            "em", "thường", "ai","cảm_ơn","bị", "bởi", "cả", "các", "cái", "cần", "càng", "chỉ", "chiếc", "chứ", "chưa", "chuyện", "có",
            "có_thể", "cứ", "của", "cùng", "cũng", "đã", "đang", "đây", "để", "đến_nỗi", "đều", "do", "đó", "được", "dưới"
            , "không", "là", "lại", "lên", "lúc", "mà", "mỗi", "một_cách", "ngay", "nhiều", "như",
            "nhưng", "những", "nơi", "nữa", "phải", "qua", "ra", "rất", "rồi", "sau", "sẽ", "so", "sự", "tại",
            "theo", "thì", "trên", "trước", "từ", "từng", "và", "vẫn", "vào", "vậy", "vì", "việc", "với"};
    static List<String> listStopword = Arrays.asList(stopWords1);

    public static String[] removeStopword(String content){
        Stream<?> stream= Arrays.stream(content.split(" "));
        String result[]=stream.filter(word->{
            return listStopword.indexOf(word)==-1;
        }).toArray(String[]::new);
        return result;
    }
}

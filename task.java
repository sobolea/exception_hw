/*Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол
Форматы данных:
фамилия, имя, отчество - строки
датарождения - строка формата dd.mm.yyyy
номертелефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.
1.Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и 
показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
2.Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, 
нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть 
корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
Не забудьте закрыть соединение с файлом.
При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.*/

// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.GregorianCalendar;
// import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class task{
    public static void main(String[] args) throws IOException {
       try {
            String[] data = inputData();
            Object[] info = new Object[8];
            info = formatData(data);
            File file = new File(String.format("%s.txt",info[0]));
            FileWriter writer = new FileWriter (file, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            if(file.createNewFile()){
                bufferWriter.newLine();
                bufferWriter.write(formatText(info));
            } else {
                 writer.write(formatText(info));
            }
            bufferWriter.close();
            writer.close();
       } catch (IncorrectSizeException e) {
        System.out.println(e);
       } catch (FormatException e) {
        System.out.println(e);
       }catch (IOException e){
        System.out.println("Проблема с файлом");
       }
        
        
    }

    public static String[] inputData() throws IncorrectSizeException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите данные через пробел: фамилия, имя, отчество, дата рождения, номер телефона, пол");
        System.out.println("В формате:\nФамилия Имя Отчество дд.мм.гг 12345 f(m)");   
        String str = sc.nextLine();
        sc.close();
        int size = str.split(" ").length;
        if(size == 6){
            return str.split(" ");
        } else {
            throw new IncorrectSizeException(String.format("Incorrect number of input data. Must be 6, but you input %d. Try one more time", size));
        }
        }

    public static Object[] formatData(String[] arr) throws FormatException{
        Object[] info = new Object[8];
        info[0] = (arr[0]);
        info[1] =(arr[1]);
        info[2] = (arr[2]);
        try {
            String[] date = arr[3].split("/");
            info[3] = Integer.parseInt(date[0]);
            info[4] = Integer.parseInt(date[1]);
            info[5] = Integer.parseInt(date[2]);
        } catch (IllegalArgumentException e) {
            throw new FormatException(String.format("Incorrect input of data. Must be dd.mm.yyyy, you have %s", arr[3]));
        }
        try {
           int nuumber = Integer.parseInt(arr[4]);
           info[6] = (nuumber);
        } catch (IllegalArgumentException e) {
            throw new FormatException("Incorrect input of phone number");
        }
        System.out.println(arr[5]);
       if(arr[5].equals("f") || arr[5].equals("m")) info[7] =(arr[5]);
        else throw new FormatException(String.format("Must be f or m. You have %s", arr[5]));
        return info;
    }

    public static String formatText(Object[] arr) {
        return String.format("%s %s %s %d.%d.%d %d %s\n", arr[0], arr[1], arr[2],arr[3], arr[4], arr[5], arr[6], arr[7]);
    }

}

//Разделение даты через точку не работает

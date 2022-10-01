package todo;
import java.util.*;
import java.util.Map.Entry;
import java.text.*;
import java.util.concurrent.TimeUnit;
public class Tasks extends Main{
    //Class wide variables
    HashMap<String, String> todoandDate = new HashMap<String, String>();
    Date curDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
    String formattedDate = new String(dateFormat.format(curDate));
    ArrayList<String> taskList = new ArrayList<String>();
    Scanner scanner = new Scanner(System.in);
    HashMap<String, Integer> daysremainingTable = new HashMap<String, Integer>();
    ArrayList<String> keyList = new ArrayList<String>();

    public void inputdata() {
        String inputdate = new String();
        String inputtask = new String();
        System.out.println("\033[H\033[2J");
        while (!inputtask.equals("Done")) {
            System.out.println("\u001b[37mWhat task would you like to add? \u001B[31m Type 'Done' to exit.\u001b[32m");
            inputtask = scanner.nextLine();
            if (inputtask.equals("Done")) {
                System.out.println("\u001b[37m");
                break;
            }
            System.out.println("\u001b[37mWhat date is this task? --Please use MM/DD/YY formatting.\u001b[32m");
            inputdate = scanner.nextLine();
            todoandDate.put(inputtask, inputdate);
            taskList.add(inputtask);
        }
    
    }
    public void Logic() throws Exception{
  
        for (Map.Entry<String, String> cur : todoandDate.entrySet()) {
        
                // Convert `String` to `Dateo
                Date dateBefore = dateFormat.parse(formattedDate);
                Date dateAfter = dateFormat.parse(cur.getValue());
            
                long timeDiff = dateAfter.getTime() - dateBefore.getTime();
                long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
                int intdaysDiff = Math.toIntExact(daysDiff);
                daysremainingTable.put(cur.getKey(), intdaysDiff);
    
        }

    }
    
    
    public void printtasks() {
        HashMap<String, Integer> sortedTasksHashMap = new LinkedHashMap<String, Integer>();
        Set<Entry<String, Integer>> set = daysremainingTable.entrySet(); 
        List<Entry<String, Integer>> list = new ArrayList<>(set);
        // Sort the list
        
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        for (Map.Entry<String, Integer> entry : list)   {  
            sortedTasksHashMap.put(entry.getKey(), entry.getValue());  
        }

        System.out.println("\033[H\033[2J\u001b[34mTodo List, Sorted by Due date: \n\u001b[37m");
        // put data from sorted list to hashmap
        if (daysremainingTable.size() == 0) {
            System.out.println("{To-Do List Empty}");
        }
        int i = 1;
        String fstring = new String();
        
        for (Map.Entry<String, Integer> aa : list) {
            sortedTasksHashMap.put(aa.getKey(), aa.getValue());
            if (aa.getValue() < 0) {
                fstring = String.format("\u001b[37m%d. " + aa.getKey() + " | \u001b[31mDue %d days ago\u001b[37m", i, Math.abs(aa.getValue()));
                System.out.println(fstring);
                keyList.add(aa.getKey());
            }
            if (aa.getValue() == 0) {
                fstring = String.format("\u001b[37m%d. " + aa.getKey() + " | \u001b[33mDue TODAY!\u001b[37m", i);
                System.out.println(fstring);
                keyList.add(aa.getKey());
            }
            if (1 <= aa.getValue() && 3 > aa.getValue()) {
                fstring = String.format("\u001b[37m%d. " + aa.getKey() + " | \u001b[33mDue in %d days\u001b[37m", i, aa.getValue());
                System.out.println(fstring);
                keyList.add(aa.getKey());
            }

            if (3 <= aa.getValue()) {
                fstring = String.format("\u001b[37m%d. " + aa.getKey() + " | \u001b[32mDue in %d days\u001b[37m", i, aa.getValue());
                System.out.println(fstring);
                keyList.add(aa.getKey());
            }
            i++;
        }
    }
    public void taskmanager() throws Exception  {
        String input = new String();
        
        while (true) {
            Logic();
            printtasks();
            System.out.println("\n\u001b[37mPress 1 to add a task, 2 to delete a task, or <enter> to exit the program.");
            input = scanner.nextLine();
            if (input.equals("1")) {
                keyList.clear();
                inputdata();
        
                continue;
            }
            else if (input.equals("2")) {
                System.out.println("\u001b[37mWhat task would you like to delete? Please use the task number.");
                input = scanner.nextLine();
                int intdelete = Integer.valueOf(input);
                daysremainingTable.remove(keyList.get(intdelete-1));
                todoandDate.remove(keyList.get(intdelete-1));
                keyList.clear();
                Logic();
                
            }

            else {
                scanner.close();
                System.exit(0);
            }
                    
        }
    }   
    

}

package todo;
class Main {
    
    public static void main(String[] args) throws Exception {
        Main instance = new Main();
        instance.run();
    }
    
    public void run() throws Exception {
        Tasks obj = new Tasks();
        obj.inputdata();
        obj.Logic();
        obj.taskmanager();
    }
    
}
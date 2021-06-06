import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Semaphore;

public class WaitingRoom {
    
    //YOU WILL NEED THIS availablePermits()
    static Queue<UserThread> userList = new LinkedList<>();
    static Queue<UserThread> userWaiting = new LinkedList<>();
    static Semaphore doctorSemaphore;
    static boolean[] doctor;
    static int doctorsNum;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //entering number of doctors
        doctorsNum = scanner.nextInt();
        doctorSemaphore = new Semaphore(doctorsNum);
        doctor = new boolean[doctorsNum];
        Arrays.fill(doctor,false);
        //entering number of chairs
        int m = scanner.nextInt();
        //number of patients
        int counter = scanner.nextInt();
        for (int i = 0; i < counter; i++) {
            String name = scanner.next();
            int inTime = scanner.nextInt();
            userList.add(new UserThread(new User(name,inTime)));
        }
        int now = LocalDateTime.now().getMinute()*60 + LocalDateTime.now().getSecond();
       // System.out.println(now);
        while (!userList.isEmpty()){
            //System.out.println(LocalDateTime.now().getSecond() - now);
            if(userList.peek().user.inTime <= (LocalDateTime.now().getMinute()*60 + LocalDateTime.now().getSecond() - now)){
                if(userWaiting.size() == m)
                    userList.remove();
                else{
                   // System.out.println("adding " + userList.peek().user.name);
                    userList.peek().user.setLocalInTime(LocalDateTime.now().getMinute()*60 + LocalDateTime.now().getSecond());
                    userWaiting.add(userList.peek());
                   // System.out.println("starting: " + userList.peek().user.name);
                    userList.peek().thread.start();
                    userList.remove();
                }
            }
        }
        //main thread should wait for other threads to be completed
       /* for (UserThread userth : userWaiting) {
            try {
                userth.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
import java.time.LocalDateTime;

public class UserThread implements Runnable{
    User user;
    Thread thread;

    public UserThread(User user) {
        this.user = user;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {

        while(true){
            if(WaitingRoom.doctorSemaphore.availablePermits() != 0) {
                int doctorName; //doctor's name
                while (true){
                    doctorName = (int)(Math.random()*WaitingRoom.doctorsNum);
                    if(WaitingRoom.doctor[doctorName])
                        continue;
                    else
                        break;
                }
                WaitingRoom.doctor[doctorName] = true;
                if (WaitingRoom.userWaiting.contains(this)) {
                    try {
                        WaitingRoom.doctorSemaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                  //  System.out.println("before sleep "+user.name + (LocalDateTime.now().getMinute()*60 + LocalDateTime.now().getSecond()));
                    WaitingRoom.userWaiting.remove(this);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                  //  System.out.println("after sleep "+user.name+ (LocalDateTime.now().getMinute()*60 + LocalDateTime.now().getSecond()));
                    System.out.println(user.name + " time: " + (LocalDateTime.now().getMinute()*60 + LocalDateTime.now().getSecond() - this.user.localInTime + this.user.inTime) + " doctor's name: doctor" + doctorName+1);
                   // System.out.println(WaitingRoom.userWaiting.toString());
                    WaitingRoom.doctor[doctorName] = false;
                    WaitingRoom.doctorSemaphore.release();
                    break;
                }
            }
        }
    }

}

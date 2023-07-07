import org.firmata4j.IOEvent;
import org.firmata4j.Pin;
import org.firmata4j.IODeviceEventListener;
import edu.princeton.cs.introcs.StdDraw;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class graph implements IODeviceEventListener{
    private final Pin button;
    private final Pin sensor;
    private final Timer timer;
    graph(Pin button, Pin sensor, Timer timer){
        this.button = button;
        this.sensor = sensor;
        this.timer = timer;
    }

    @Override
    public void onPinChange(IOEvent ioEvent) {
        if (button.getValue() != 1) {
            return;
        } else {
            StdDraw.setXscale(-3, 100);
            StdDraw.setYscale(-30, 1100);
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(0,0,0,1000);
            StdDraw.line(0,0, 100, 0);
            StdDraw.text(50, 1100, "Analog vs. Time");
            StdDraw.text(50, -70, "Time (S)");
            StdDraw.text(5, 1050, "Analog (quanta)");
            StdDraw.text(0, -30, "0");
            StdDraw.text(25, -30, "25");
            StdDraw.text(50, -30, "50");
            StdDraw.text(75, -30, "75");
            StdDraw.text(100, -30, "100");
            StdDraw.text(-5, 250, "250");
            StdDraw.text(-5, 500, "500");
            StdDraw.text(-5, 750, "750");
            StdDraw.text(-4, 1000, "1000");
            HashMap<Integer, Integer> pairedValues = new HashMap<>();
            final int[] sample = {1};
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    var senVal = (int)sensor.getValue();
                    pairedValues.put(sample[0], senVal);
                    pairedValues.forEach((xValue, yValue) -> StdDraw.text(xValue, yValue, "-"));
                    if (sample[0] < 100) {
                        sample[0]++;
                    } else {
                        sample[0] = 1;
                    }
                }
            }, 0, 250);
        }
    }

    @Override
    public void onStart(IOEvent ioEvent) {}

    @Override
    public void onStop(IOEvent ioEvent) {}

    @Override
    public void onMessageReceive(IOEvent ioEvent, String s) {}
}

import java.io.IOException;
import java.util.TimerTask;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import java.util.Timer;

public class waterTask extends TimerTask {
    private final Pin led;
    private final Pin sensor;
    private final SSD1306 oled;
    private final Pin pump;



    waterTask(Pin led, Pin sensor, SSD1306 oled, Pin pump) {
        this.led = led;
        this.sensor = sensor;
        this.oled = oled;
        this.pump = pump;
    }

    @Override
    public void run() {
        try {
            this.oled.getCanvas().clear();
            this.oled.getCanvas().setTextsize(1);
            this.oled.getCanvas().drawString(0, 10, "Sensor Value: \n" + this.sensor.getValue());
            this.oled.display();

            int dryVal = 720;
            int moistVal = 615;
            int saturatedVal = 570;
            if (this.sensor.getValue() > moistVal && this.sensor.getValue() < dryVal || this.sensor.getValue() > dryVal) {
                this.led.setValue(1);
                System.out.println("The soil is dry!" + " Analog Reading: " + this.sensor.getValue());
                this.pump.setValue(1);
            } else if (this.sensor.getValue() > saturatedVal && this.sensor.getValue() < moistVal) {
                this.led.setValue(1);
                System.out.println("The soil is damp." + " Analog Reading: " + this.sensor.getValue());
                this.pump.setValue(1);
            } else if (this.sensor.getValue() <= saturatedVal) {
                System.out.println("The soil is saturated! :)" + " Analog Reading: " + this.sensor.getValue());
                this.led.setValue(0);
                this.pump.setValue(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

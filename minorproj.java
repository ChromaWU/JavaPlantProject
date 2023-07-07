import org.firmata4j.I2CDevice;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;
import java.util.Timer;

public class minorproj {
    public static void main(String[] args) {
        try {
            //initialization of board
            IODevice arduinoObject = new FirmataDevice("COM3");
            arduinoObject.start();
            arduinoObject.ensureInitializationIsDone();
            //oled initialization
            I2CDevice i2cObject = arduinoObject.getI2CDevice((byte) 0x3C);
            SSD1306 display = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
            display.init();
            //pin initialization
            var moistSen = arduinoObject.getPin(Pins.A1); //moisture sensor
            moistSen.setMode(Pin.Mode.ANALOG);            //converts analog to volts

            var ledLight = arduinoObject.getPin(Pins.D4); //led light
            ledLight.setMode(Pin.Mode.OUTPUT);

            var button = arduinoObject.getPin(Pins.D6); // button
            button.setMode(Pin.Mode.INPUT);

            var waterPump = arduinoObject.getPin(7); // water pump
            waterPump.setMode(Pin.Mode.OUTPUT);

            Timer timer = new Timer();
            var pumpingWater = new waterTask(ledLight, moistSen, display, waterPump);
            var buttonGraph = new graph(button, moistSen, timer);
            timer.schedule(pumpingWater, 0, 250);
            arduinoObject.addEventListener(buttonGraph);

        } catch(Exception ex) {
            ex.printStackTrace();
        }



    }
}

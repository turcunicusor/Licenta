package Concrete;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;

public class SensorTH {
    private static final int MAXTIMINGS = 85;
    private final int[] SensorData = {0, 0, 0, 0, 0};
    private boolean dataReadOk = false;

    private float humidity;
    private float temperature;

    public SensorTH() {
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }
        humidity = 0.0f;
        temperature = 0.0f;
        GpioUtil.export(3, GpioUtil.DIRECTION_OUT);
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void getTemperature(final int pin) {
        int laststate = Gpio.HIGH;
        int j = 0;
        SensorData[0] = SensorData[1] = SensorData[2] = SensorData[3] = SensorData[4] = 0;

        Gpio.pinMode(pin, Gpio.OUTPUT);
        Gpio.digitalWrite(pin, Gpio.LOW);
        Gpio.delay(18);

        Gpio.digitalWrite(pin, Gpio.HIGH);
        Gpio.pinMode(pin, Gpio.INPUT);

        for (int i = 0; i < MAXTIMINGS; i++) {
            int counter = 0;
            while (Gpio.digitalRead(pin) == laststate) {
                counter++;
                Gpio.delayMicroseconds(1);
                if (counter == 255) {
                    break;
                }
            }

            laststate = Gpio.digitalRead(pin);

            if (counter == 255) {
                break;
            }

            /* ignore first 3 transitions */
            if (i >= 4 && i % 2 == 0) {
                /* shove each bit into the storage bytes */
                SensorData[j / 8] <<= 1;
                if (counter > 16) {
                    SensorData[j / 8] |= 1;
                }
                j++;
            }
        }

        if (j >= 40 && checkParity()) {
            humidity = (float) ((SensorData[0] << 8) + SensorData[1]) / 10;
            if (humidity > 100) {
                humidity = SensorData[0];
            }
            temperature = (float) (((SensorData[2] & 0x7F) << 8) + SensorData[3]) / 10;
            if (temperature > 125) {
                temperature = SensorData[2];
            }
            if ((SensorData[2] & 0x80) != 0) {
                temperature = -temperature;
            }
//            this.dataReadOk = true;
//            System.out.println("Humidity = " + humidity + " Temperature = " + temperature);
        } else {
//            this.dataReadOk = false;
//            System.out.println("Data not good, skip");
        }
    }

    private boolean checkParity() {
        return SensorData[4] == (SensorData[0] + SensorData[1] + SensorData[2] + SensorData[3] & 0xFF);
    }

    public void readData(int pin) {
//        while(!dataReadOk) {
//            Thread.sleep(400);
//            this.getTemperature(pin);
//        }
        Runnable r = () -> {
            while (true) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getTemperature(pin);
            }
        };
        new Thread(r).start();
//        this.dataReadOk = false;
//        System.out.println("readData Done!!");
    }
}
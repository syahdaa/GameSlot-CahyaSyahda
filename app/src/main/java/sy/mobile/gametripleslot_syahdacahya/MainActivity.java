package sy.mobile.gametripleslot_syahdacahya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivSlot1, ivSlot2, ivSlot3;
    private Thread thread1, thread2, thread3;
    private ArrayList<Integer> arImage;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btStartStop).setOnClickListener(this);

        arImage = new ArrayList<Integer>();
        arImage.add(R.drawable.slot_1);
        arImage.add(R.drawable.slot_2);
        arImage.add(R.drawable.slot_3);
        arImage.add(R.drawable.slot_4);
        arImage.add(R.drawable.slot_5);
        arImage.add(R.drawable.slot_6);
        arImage.add(R.drawable.slot_7);
        arImage.add(R.drawable.slot_8);
        arImage.add(R.drawable.slot_9);

        this.handler = new Handler(Looper.getMainLooper());
        this.ivSlot1 = (ImageView) findViewById(R.id.ivSlot1);
        this.ivSlot2 = (ImageView) findViewById(R.id.ivSlot2);
        this.ivSlot3 = (ImageView) findViewById(R.id.ivSlot3);

        ivSlot1.setBackgroundResource(R.drawable.slot_9);
        ivSlot2.setBackgroundResource(R.drawable.slot_9);
        ivSlot3.setBackgroundResource(R.drawable.slot_9);

        // Create the threads, but don't start them yet
        thread1 = new SlotThread(ivSlot1, handler, arImage);
        thread2 = new SlotThread(ivSlot2, handler, arImage);
        thread3 = new SlotThread(ivSlot3, handler, arImage);
    }

    private class SlotThread extends Thread {
        private ImageView ivSlot;
        private Handler handler;
        private ArrayList<Integer> arImage;

        public SlotThread(ImageView ivSlot, Handler handler, ArrayList<Integer> arImage) {
            this.ivSlot = ivSlot;
            this.handler = handler;
            this.arImage = arImage;
        }

        @Override
        public void run() {
            Random rand = new Random();
            while (!isInterrupted()) {
                int index = rand.nextInt(9);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot.setBackgroundResource(arImage.get(index));
                    }
                });

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (thread1.isAlive()) {
            thread1.interrupt();
        } else if (thread2.isAlive()) {
            thread2.interrupt();
        } else if (thread3.isAlive()) {
            thread3.interrupt();
        } else {
            thread1.start();
            thread2.start();
            thread3.start();
        }
    }
}
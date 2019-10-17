package the.inner.circle;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private CharacterSprite characterSprite;


    public GameView(Context c) {
        super(c);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(), R.drawable.innercircle));

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(){
        characterSprite.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            characterSprite.draw(canvas);
        }
    }
}

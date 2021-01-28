package ro.mirodone.slotm.ImageViewScrolling;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ro.mirodone.slotm.R;

public class ImageViewScrolling extends FrameLayout {

    public static int ANIMATION_DUR = 150;

    ImageView current_image, next_image;

    int last_result = 0, old_value = 0;

    IEventEnd eventEnd;

    public ImageViewScrolling(@NonNull Context context) {
        super(context);
        init(context);
    }


    public ImageViewScrolling(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setEventEnd(IEventEnd eventEnd) {
        this.eventEnd = eventEnd;
    }

    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.image_view_scrolling, this);
        current_image = getRootView().findViewById(R.id.current_image);
        next_image = getRootView().findViewById(R.id.next_image);

        next_image.setTranslationY(getHeight());

    }

    public void setValueRandom(final int image, final int rotate_count) {

        current_image.animate().translationY(-getHeight()).setDuration(ANIMATION_DUR).start();
        next_image.setTranslationY(next_image.getHeight());
        next_image.animate().translationY(0).setDuration(ANIMATION_DUR).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setImage(current_image, old_value % 7);//6 images , we will mod for 6
                current_image.setTranslationY(0);
                if (old_value != rotate_count) {

                    //if old_value still not equal rotate_count, will still roll
                    setValueRandom(image, rotate_count);
                    old_value++;
                } else {
                    last_result = 0;
                    old_value = 0;
                    setImage(next_image, image);
                    eventEnd.eventEnd(image % 7, rotate_count);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void setImage(ImageView image_view, int value) {

        if (value == Util.BAR) {
            image_view.setImageResource(R.mipmap.bar_done);
        } else if (value == Util.SEVEN) {
            image_view.setImageResource(R.mipmap.sevent_done);
        } else if (value == Util.LEMON) {
            image_view.setImageResource(R.mipmap.lemon_done);
        } else if (value == Util.ORANGE) {
            image_view.setImageResource(R.mipmap.orange_done);
        } else if (value == Util.TRIPLE) {
            image_view.setImageResource(R.mipmap.triple_done);
        } else if (value == Util.WATERMELON) {
            image_view.setImageResource(R.mipmap.waternelon_done);
        } else if (value == Util.CHERRY) {
            image_view.setImageResource(R.mipmap.cherry_done);
        }

        //set tag for image for use to compare result
        image_view.setTag(value);
        last_result = value;

    }

    public int getValue() {
        return Integer.parseInt(next_image.getTag().toString());
    }

}

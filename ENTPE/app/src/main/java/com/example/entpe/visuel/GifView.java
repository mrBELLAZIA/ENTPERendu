package com.example.entpe.visuel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.entpe.R;

public class GifView extends View {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * GIF
     */
    private Movie movie;

    /**
     * Début du GIF
     */
    private long movieStart;

    /*
     * Peinture
     */
    //private Paint paint;

    /**
     * Métriques d'affichage
     */
    private DisplayMetrics displayMetrics;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Simple constructor when creating a view
     * @param context is the context in which the view is used
     */
    public GifView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Constructeur gonflé à partir d'un fichier XML
     * @param context est le contexte d'utilisation de la vue
     * @param attrSet est le jeu d'attributs
     */
    public GifView(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        init(context);
    }

    /**
     *
     * @param context est le contexte d'utilisation de la vue
     * @param attrSet est le jeu d'attributs
     * @param defStyle est une valeur recherchée pour un style par défaut
     */
    public GifView(Context context, AttributeSet attrSet, int defStyle) {
        super(context, attrSet, defStyle);
        init(context);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Métriques pour la taille de l'écran
     */
    private void init(Context context) {
        setFocusable(true);

        Paint p = new Paint();
        p.setAntiAlias(true);

        movie = Movie.decodeStream(context.getResources().openRawResource(R.raw.emotentpe));

        displayMetrics = new DisplayMetrics();
    }

    /**
     * Affichage
     * @param canvas est la toile sur laquelle est dessinée le gif
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.background, null));

        long now = android.os.SystemClock.uptimeMillis();

        if (movieStart == 0) // Début
            movieStart = now;

        int dur = movie.duration();

        if (dur == 0)
            dur = 1000;

        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        int relTime = (int) ((now - movieStart) % dur);
        movie.setTime(relTime);
        canvas.scale(1,1,(float) getWidth()/2,(float) getHeight()/2);
        movie.draw(canvas, (float)(getWidth() - movie.width())/2, (float)(getHeight() - movie.height())/2);
        invalidate();
    }
}

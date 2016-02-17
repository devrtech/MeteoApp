package fr.devrtech.meteoapp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import fr.devrtech.meteoapp.R;

/**
 * Item displayed in mosaic mode of recyclerview (square cardview)
 * <p/>
 * Created by remi on 17/02/16.
 */
public class MosaicItemView extends CardView {

    /**
     * Constructor
     */
    public MosaicItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set a square layout.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    /**
     * Setup view
     */
    protected void init() {
        // Merge with the xml
        LayoutInflater.from(getContext()).inflate(R.layout.item_place, this, true);
        // For displying card with margin/padding decoration
        setUseCompatPadding(true);
        /* *** PART for RIPPLE *** */
        // Create an array of the attributes we want to resolve
        // using values from a theme
        // android.R.attr.selectableItemBackground requires API LEVEL 11
        int[] attrs = new int[]{android.R.attr.selectableItemBackground /* index 0 */};
        // Obtain the styled attributes. 'themedContext' is a context with a
        // theme, typically the current Activity (i.e. 'this')
        TypedArray ta = getContext().obtainStyledAttributes(attrs);
        // Now get the value of the 'listItemBackground' attribute that was
        // set in the theme used in 'themedContext'. The parameter is the index
        // of the attribute in the 'attrs' array. The returned Drawable
        // is what you are after
        Drawable drawableFromTheme = ta.getDrawable(0 /* index */);
        // Finally free resources used by TypedArray
        ta.recycle();
        // setBackground(Drawable) requires API LEVEL 16,
        // otherwise you have to use deprecated setBackgroundDrawable(Drawable) method.
        setForeground(drawableFromTheme);
    }

}

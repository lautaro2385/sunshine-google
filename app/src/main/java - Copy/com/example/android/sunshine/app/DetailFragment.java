package com.example.android.sunshine.app;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.sunshine.app.data.WeatherContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String DETAIL_URI = "URI";
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private static final String FORECAST_SHARE_HASHTAG = "#sunshineApp";
    //private String mForecastStr;
    private ShareActionProvider mShareActionProvider;

    private static final int DETAIL_LOADER = 0;
    private String mForecast;
    private Uri mUri;

    private static final String[] DETAIL_COLUMNS = {
            WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContract.WeatherEntry.COLUMN_PRESSURE,
            WeatherContract.WeatherEntry.COLUMN_WIND_SPEED,
            WeatherContract.WeatherEntry.COLUMN_DEGREES,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
            // This works because the WeatherProvider returns location data joined with
            // weather data, even though they're stored in two different tables.
            WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING
    };

    // These indices are tied to DETAIL_COLUMNS.  If DETAIL_COLUMNS changes, these
    // must change.
    public static final int COL_WEATHER_ID = 0;
    public static final int COL_WEATHER_DATE = 1;
    public static final int COL_WEATHER_DESC = 2;
    public static final int COL_WEATHER_MAX_TEMP = 3;
    public static final int COL_WEATHER_MIN_TEMP = 4;
    public static final int COL_WEATHER_HUMIDITY = 5;
    public static final int COL_WEATHER_PRESSURE = 6;
    public static final int COL_WEATHER_WIND_SPEED = 7;
    public static final int COL_WEATHER_DEGREES = 8;
    public static final int COL_WEATHER_CONDITION_ID = 9;

    private ImageView mIconView;
    private TextView mFriendlyDateView;
    private TextView mDateView;
    private TextView mDescriptionView;
    private TextView mHighTempView;
    private TextView mLowTempView;
    private TextView mHumidityView;
    private TextView mWindView;
    private TextView mPressureView;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*
            Intent DetailIntent = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if (DetailIntent != null && DetailIntent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecastStr = DetailIntent.getStringExtra(Intent.EXTRA_TEXT);
                TextView tv = (TextView) rootView.findViewById(R.id.detail_text);
                tv.setText(mForecastStr);
            } else if (DetailIntent != null) {
                mForecastStr = DetailIntent.getDataString();
                TextView tv = (TextView) rootView.findViewById(R.id.detail_text);
                tv.setText(mForecastStr);
            }*/
        //return inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle arguments = getArguments();
        if (arguments != null)
            mUri = arguments.getParcelable(DETAIL_URI);

        View rootView = inflater.inflate(R.layout.detail_forecast, container, false);
        mIconView = (ImageView) rootView.findViewById(R.id.detail_icon);
        mDateView = (TextView) rootView.findViewById(R.id.detail_date_textview);
        mFriendlyDateView = (TextView) rootView.findViewById(R.id.detail_day_textview);
        mDescriptionView = (TextView) rootView.findViewById(R.id.detail_forecast_textview);
        mHighTempView = (TextView) rootView.findViewById(R.id.detail_high_textview);
        mLowTempView = (TextView) rootView.findViewById(R.id.detail_low_textview);
        mHumidityView = (TextView) rootView.findViewById(R.id.detail_humidity_textview);
        mWindView = (TextView) rootView.findViewById(R.id.detail_wind_textview);
        mPressureView = (TextView) rootView.findViewById(R.id.detail_pressure_textview);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if (mShareActionProvider != null) {
            // If onLoadFinished happens before this, we can go ahead and set the share intent now.
            if (mForecast != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            }
        } else {
            Log.d(LOG_TAG, "Share Action provider is null");
        }
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecast + FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        Log.v(LOG_TAG, "In onCreateLoader");
//        Intent intent = getActivity().getIntent();
//        if (intent == null || intent.getData() == null)
//            return null;
//
//        return new CursorLoader(
//                getActivity(),
//                intent.getData(),
//                DETAIL_COLUMNS,
//                null,
//                null,
//                null);

        Log.v(LOG_TAG, "In onCreateLoader");
        if (mUri != null) {
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        /*Log.v(LOG_TAG, "In onLoadFinished");
        if (!data.moveToFirst()) {
            return;
        }

        String date = Utility.formatDate(data.getLong(COL_WEATHER_DATE));
        String desc = data.getString(COL_WEATHER_DESC);
        Boolean ismetric = Utility.isMetric(getActivity());
        String max = Utility.formatTemperature(getActivity(), data.getDouble(COL_WEATHER_MAX_TEMP), ismetric);
        String min = Utility.formatTemperature(getActivity(), data.getDouble(COL_WEATHER_MIN_TEMP), ismetric);

        mForecast = String.format("%s - %s %s-%s", date, desc, max, min);

        TextView tv = (TextView) getView().findViewById(R.id.detail_text);
        tv.setText(mForecast);

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }*/

        Log.v(LOG_TAG, "In onLoadFinished");
        if (data != null && data.moveToFirst()) {
            // Read weather condition ID from cursor
            int weatherId = data.getInt(COL_WEATHER_CONDITION_ID);
            // Use placeholder Image
            // mIconView.setImageResource(Utility.getArtResourceForWeatherCondition(weatherId));
            Glide.with(this)
                    .load(Utility.getArtUrlForWeatherCondition(getActivity(), weatherId))
                    .error(Utility.getArtResourceForWeatherCondition(weatherId))
                    .crossFade()
                    .into(mIconView);

            // Read date from cursor and update views for day of week and date
            long date = data.getLong(COL_WEATHER_DATE);
            String friendlyDateText = Utility.getDayName(getActivity(), date);
            String dateText = Utility.getFormattedMonthDay(getActivity(), date);
            mFriendlyDateView.setText(friendlyDateText);
            mDateView.setText(dateText);

            // Read description from cursor and update view
            String description = data.getString(COL_WEATHER_DESC);
            mDescriptionView.setText(description);

            // Read high temperature from cursor and update view
            boolean isMetric = Utility.isMetric(getActivity());

            double high = data.getDouble(COL_WEATHER_MAX_TEMP);
            String highString = Utility.formatTemperature(getActivity(), high, isMetric);
            mHighTempView.setText(highString);

            // Read low temperature from cursor and update view
            double low = data.getDouble(COL_WEATHER_MIN_TEMP);
            String lowString = Utility.formatTemperature(getActivity(), low, isMetric);
            mLowTempView.setText(lowString);

            // Read humidity from cursor and update view
            float humidity = data.getFloat(COL_WEATHER_HUMIDITY);
            mHumidityView.setText(getActivity().getString(R.string.format_humidity, humidity));

            // Read wind speed and direction from cursor and update view
            float windSpeedStr = data.getFloat(COL_WEATHER_WIND_SPEED);
            float windDirStr = data.getFloat(COL_WEATHER_DEGREES);
            mWindView.setText(Utility.getFormattedWind(getActivity(), windSpeedStr, windDirStr));

            // Read pressure from cursor and update view
            float pressure = data.getFloat(COL_WEATHER_PRESSURE);
            mPressureView.setText(getActivity().getString(R.string.format_pressure, pressure));

            // We still need this for the share intent
            mForecast = String.format("%s - %s - %s/%s", dateText, description, high, low);

            // If onCreateOptionsMenu has already happened, we need to update the share intent now.
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    void onLocationChanged(String newLocation) {
        Uri uri = mUri;

        if (uri != null) {
            long date = WeatherContract.WeatherEntry.getDateFromUri(uri);
            uri = WeatherContract.WeatherEntry.buildWeatherLocationWithDate(newLocation, date);
            mUri = uri;
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
        }
    }
}
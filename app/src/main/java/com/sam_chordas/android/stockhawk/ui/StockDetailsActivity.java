package com.sam_chordas.android.stockhawk.ui;


import android.app.LoaderManager;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.async_retrofit.Query;
import com.sam_chordas.android.stockhawk.async_retrofit.Quote;
import com.sam_chordas.android.stockhawk.async_retrofit.Results;
import com.sam_chordas.android.stockhawk.async_retrofit.RetrofitService;
import com.sam_chordas.android.stockhawk.async_retrofit.StockHistory;
import com.sam_chordas.android.stockhawk.data.HistoryColumns;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.AsyncQueryHandler;
import com.sam_chordas.android.stockhawk.rest.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String BASE_URL = "https://query.yahooapis.com/";
    private static final int DEFAULT_RANGE_WEEK = 7;
    private static final int RANGE_MONTH = 30;
    private static String symbol;
    private static String name;
    private static String range = "week";
    private final String LOG_TAG = StockDetailsActivity.class.getSimpleName();
    boolean isConnected;
    RetrofitService service;
    private Context mContext;
    private Cursor mCursor;
    private LineChart lineChartView;
    private ProgressWheel progressView;
    private ArrayList<ContentProviderOperation> batchHistOperations = new ArrayList<>();
    private List<Quote> quote;
    private TextView err_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        setContentView(R.layout.activity_line_graph);
        err_txt = (TextView) findViewById(R.id.emptyview);
        lineChartView = (LineChart) findViewById(R.id.linechart);
        progressView = (ProgressWheel) findViewById(R.id.progress_wheel);
        Bundle extras = getIntent().getExtras();
        symbol = extras.getString(MyStocksActivity.EXTRA_SYMBOL);
        Log.i(LOG_TAG, "symbol in oncreate " + symbol);
        name = extras.getString(MyStocksActivity.EXTRA_NAME);
        if (name != null && !name.isEmpty()) {
            ActionBar mActionBar = getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.setTitle(name);
            }
            lineChartView.setNoDataText("");
            if (savedInstanceState == null) {
                if (isConnected) {
                    progressView.setVisibility(View.VISIBLE);
                    range = "week";
                    getHistoricData(DEFAULT_RANGE_WEEK);
                } else {
                    err_txt.setVisibility(View.VISIBLE);
                }
            }
            getLoaderManager().initLoader(MyStocksActivity.CURSOR_LOADER_ID, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_stocks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_week_history: {
                if (!range.equals("week")) {
                    Log.i(LOG_TAG, " graph for a week");
                    getHistoricData(DEFAULT_RANGE_WEEK);
                    range = "week";
                }
                break;
            }
            case R.id.action_month_history: {
                if (!range.equals("month")) {
                    Log.i(LOG_TAG, " graph for a month");
                    getHistoricData(RANGE_MONTH);
                    range = "month";
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /* Getting the stock history using Retrofit Service*/
    public void getHistoricData(int daysSinceToday) {

        //getting stock data since a week(no data for sat & sun)
        String startDate = Utils.getFormattedDate(daysSinceToday);
        //getting today's date for end date
        final String endDate = Utils.getFormattedDate(0);
        Log.i(LOG_TAG, "start_date  " + startDate + "  end_date " + endDate);
        String query = "select * from yahoo.finance.historicaldata where symbol = \"" + symbol
                + "\" and startDate = \"" + startDate + "\" and endDate = \"" + endDate + "\"";

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = builder.create(RetrofitService.class);
        Call<StockHistory> mStockHistory = service.getStockHistory(query);
        mStockHistory.enqueue(new Callback<StockHistory>() {
            @Override
            public void onResponse(Call<StockHistory> call, Response<StockHistory> response) {
                try {
                    Log.i(LOG_TAG, response.raw().toString());
                    StockHistory history = response.body();
                    Query query = history.getQuery();
                    Results results = query.getResults();
                    quote = results.getQuote();
                    Log.i(LOG_TAG, "symbol is " + quote.get(0).getSymbol());
                    // Using AsyncQueryHandler object for querying content provider in the background,
                    // instead of from the UI thread
                    AsyncQueryHandler queryHandler = new AsyncQueryHandler(getContentResolver()) {
                        @Override
                        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                            super.onQueryComplete(token, cookie, cursor);
                            if ((cursor!=null) && (cursor.getCount() != 0) ){
                                cursor.moveToFirst();
                                ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                                        QuoteProvider.History.CONTENT_URI);
                                builder.withValue(HistoryColumns.SYMBOL, quote.get(0).getSymbol());
                                float price = Float.parseFloat(cursor.getString(cursor.getColumnIndex(QuoteColumns.BIDPRICE)));
                                builder.withValue(HistoryColumns.VALUE, price);
                                builder.withValue(HistoryColumns.DATE, endDate);
                                batchHistOperations.add(builder.build());
                            }
                        }

                        @Override
                        protected void onDeleteComplete(int token, Object cookie, int result) {
                            super.onDeleteComplete(token, cookie, result);
                            Log.i(LOG_TAG, "Deleted " + result +" records successfully");
                        }
                    };
                    // Construct query and execute
                    queryHandler.startDelete(
                            1, null,
                            QuoteProvider.History.CONTENT_URI,
                            null, null
                    );



                    if(!(quote.get(0).getDate()).equals(endDate)){
                        Log.i(LOG_TAG,"adding todays stock from quote db");
                        queryHandler.startQuery(2, null,
                                QuoteProvider.Quotes.withSymbol(quote.get(0).getSymbol()),
                                new String[]{QuoteColumns.BIDPRICE},
                                QuoteColumns.ISCURRENT + "= ?",
                                new String[]{"1"},
                                null);
//                        Cursor cursor = c.getContentResolver().query(
//                                QuoteProvider.Quotes.withSymbol(quote.get(0).getSymbol()),
//                                new String[]{QuoteColumns.BIDPRICE},
//                                QuoteColumns.ISCURRENT + "= ?",
//                                new String[]{"1"},
//                                null);
                    }
                    for (Quote q : quote) {
                        if (q != null) {
                            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                                    QuoteProvider.History.CONTENT_URI);
                            builder.withValue(HistoryColumns.SYMBOL, q.getSymbol());
                            builder.withValue(HistoryColumns.VALUE, Float.parseFloat(q.getClose()));
                            builder.withValue(HistoryColumns.DATE, q.getDate());
                            batchHistOperations.add(builder.build());
                        }
                    }

//                    mContext.getContentResolver().delete(
//                            QuoteProvider.History.CONTENT_URI,
//                            null, null);
                    try {
                        mContext.getContentResolver().applyBatch(QuoteProvider.AUTHORITY,
                                batchHistOperations);
                    } catch (RemoteException | OperationApplicationException e) {
                        Log.e(LOG_TAG, "Error applying batch insert", e);
                    }
                } catch (NullPointerException e) {
                    if (response.code() == 401) {
                        Log.i(LOG_TAG, "Unauthenticated");
                    } else if (response.code() >= 400) {
                        Log.i(LOG_TAG, "Client Error " + response.code() + " " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<StockHistory> call, Throwable t) {
                err_txt.setText(String.format(getString(R.string.something_wrong), t.getMessage()));
                Log.d("error in the response", t.toString());
                t.printStackTrace();
            }


        });
    }

    private void drawLineChart() {
        mCursor.moveToFirst();
        ArrayList<Entry> entries = new ArrayList<>();
        // creating labels
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i < mCursor.getCount(); i++) {
            String dateStr = mCursor.getString(mCursor.getColumnIndex(HistoryColumns.DATE));
            float price = Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(HistoryColumns.VALUE)));
            entries.add(new Entry(price, i));
            labels.add(dateStr.substring(5, 10));
            mCursor.moveToNext();
        }
        LineDataSet dataset = new LineDataSet(entries, "previous " + range);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setLineWidth(3);
        dataset.setDrawValues(false);
        LineData data = new LineData(labels, dataset);
        progressView.setVisibility(View.GONE);
        lineChartView.setDescription(symbol);  // set the description
        lineChartView.setDescriptionTextSize(12f);
        lineChartView.animateX(2000);
        lineChartView.getXAxis().setTextSize(12f);

        lineChartView.setData(data); // set the data and list of lables into chart
        //to set the zoom
        lineChartView.setAutoScaleMinMaxEnabled(true);
        lineChartView.getAxisLeft().setStartAtZero(false);
        lineChartView.getAxisRight().setStartAtZero(false);
        lineChartView.invalidate();
        String desc = String.format(Locale.US, getString(R.string.chart_view_desc), name, range);
        lineChartView.setContentDescription(desc);
    }


    //Using the data from the database.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, QuoteProvider.History.CONTENT_URI,
                null,
                QuoteColumns.SYMBOL + " = ?",
                new String[]{symbol},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() != 0) {
//            Log.i(LOG_TAG, "stock data history available!");
            mCursor = data;
            drawLineChart();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

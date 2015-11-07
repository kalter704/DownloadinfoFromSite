package com.example.vasiliy.downloadinfofromsite;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    final String LOG_TAG_ERROR = "Error";
    final String LOG_TAG = "Info";

    String[] siteLinks = {
            "https://my-hit.org/film/322283/",
            "https://my-hit.org/film/398576/",
            "https://my-hit.org/film/415223/",
            "https://my-hit.org/film/415226/",
            "https://my-hit.org/film/414894/",
            "https://my-hit.org/film/414502/",
            "https://my-hit.org/serial/148/"
    };

    List<FilmObjectForDownload> filmObjectForDownloads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Находим кнопку
        Button button = (Button)findViewById(R.id.parse);
        //Регистрируем onClick слушателя
        button.setOnClickListener(myListener);

        filmObjectForDownloads = new ArrayList<>();

    }

    //Диалог ожидания
    private ProgressDialog pd;
    //Слушатель OnClickListener для нашей кнопки
    private View.OnClickListener myListener = new View.OnClickListener() {
        public void onClick(View v) {
            //Показываем диалог ожидания
            pd = ProgressDialog.show(MainActivity.this, "Working...", "request to server", true, false);
            //Запускаем парсинг
            new ParseSite().execute();
        }
    };

    private class ParseSite extends AsyncTask<String, Void, List<String>> {
        //Фоновая операция
        //protected List<String> doInBackground(String... arg) {
        protected List<String> doInBackground(String... arg) {
            try
            {
                for(int i = 0; i < siteLinks.length; ++i) {
                    HtmlHelper hh = new HtmlHelper(new URL(siteLinks[i]));
                    filmObjectForDownloads.add(hh.getFilmFromSite());

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        //Событие по окончанию парсинга
        protected void onPostExecute(List<String> output) {
            //Убираем диалог загрузки
            pd.dismiss();

            LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
            LayoutInflater ltInflater = getLayoutInflater();

            linLayout.removeAllViews();

            int i = 0;

            for(FilmObjectForDownload filmObjectForDownload : filmObjectForDownloads) {

                View item = ltInflater.inflate(R.layout.item_film, linLayout, false);
                TextView tvTitle = (TextView) item.findViewById(R.id.tvTitle);
                tvTitle.setText(filmObjectForDownload.getTitle());

                TextView tvYear = (TextView) item.findViewById(R.id.tvYear);
                tvYear.setText(filmObjectForDownload.getYear());

                TextView tvGanres = (TextView) item.findViewById(R.id.tvGanres);
                tvGanres.setText(filmObjectForDownload.getStringGanres());

                TextView tvCountrys = (TextView) item.findViewById(R.id.tvCountrys);
                tvCountrys.setText(filmObjectForDownload.getStringCountry());

                TextView tvDirector = (TextView) item.findViewById(R.id.tvDirector);
                tvDirector.setText(filmObjectForDownload.getDirector());

                TextView tvActors = (TextView) item.findViewById(R.id.tvActors);
                tvActors.setText(filmObjectForDownload.getStringActors());

                TextView tvDescription = (TextView) item.findViewById(R.id.tvDescription);
                tvDescription.setText(filmObjectForDownload.getDescription());

                if(i % 2 == 0) {
                    item.setBackgroundColor(Color.rgb(122, 205, 155));
                } else {
                    item.setBackgroundColor(Color.rgb(124, 122, 205));
                }

                ++i;

                linLayout.addView(item);
            }
        }

    }

}
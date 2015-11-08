package com.example.vasiliy.downloadinfofromsite;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlHelper {
    String PATERN = "^[а-яА-Яa-zA-Z\\-\\s]+$";

    TagNode rootNode;

    //Конструктор
    public HtmlHelper(URL htmlPage) throws IOException
    {
        //Создаём объект HtmlCleaner
        HtmlCleaner cleaner = new HtmlCleaner();
        //Загружаем html код сайта
        rootNode = cleaner.clean(htmlPage);
    }

    List<TagNode> getLinksByClass(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        //Выбираем все ссылки
        TagNode linkElements[] = rootNode.getElementsByName("h1", true);
        for (int i = 0; linkElements != null && i < linkElements.length; i++)
        {
            //получаем атрибут по имени
            //String classType = linkElements[i].getAttributeByName("class");
            //если атрибут есть и он эквивалентен искомому, то добавляем в список
            //if (classType != null && classType.equals(CSSClassname))
            //{
                linkList.add(linkElements[i]);
            //}
        }

        return linkList;
    }

    FilmObjectForDownload getFilmFromSite() {
        FilmObjectForDownload filmObjectForDownload = new FilmObjectForDownload();

        String title = rootNode.getElementsByName("h1", true)[0].getText().toString();
        title = title.replaceAll("&nbsp;", " ");
        filmObjectForDownload.setTitle(title);

        String filmYear = rootNode.getElementsByName("h1", true)[0].getElementsByName("a", true)[0].getText().toString();
        filmObjectForDownload.setYear(filmYear);

        TagNode[] ulElements = rootNode.getElementsByName("ul", true);

        for(int j = 0; j < ulElements.length; ++j) {
            String classType = ulElements[j].getAttributeByName("class");

            boolean find = false;
            boolean findYear = false;

            if(classType != null && classType.equals("list-unstyled")) {
                TagNode[] liElements = ulElements[j].getElementsByName("li", true);

                for(int q = 0; q < liElements.length; ++q) {

                    String str = liElements[q].getElementsByName("b", true)[0].getText().toString();

                    if("Жанр:".equals(str)) {
                        TagNode[] ganreElements = liElements[q].getElementsByName("a", true);
                        for (int i = 0; i < ganreElements.length; ++i) {
                            filmObjectForDownload.addGanre(ganreElements[i].getText().toString());
                        }
                    }

                    if("Страна:".equals(str)) {
                        TagNode[] countryElements = liElements[q].getElementsByName("a", true);
                        for (int i = 0; i < countryElements.length; ++i) {
                            filmObjectForDownload.addCountry(countryElements[i].getText().toString());
                        }
                    }

                    if("Режиссер:".equals(str)) {
                        TagNode[] director = liElements[q].getElementsByName("a", true);
                        filmObjectForDownload.setDirector(director[0].getText().toString());
                    }

                    if("В ролях:".equals(str)) {
                        TagNode[] actorsElements = liElements[q].getElementsByName("a", true);
                        for (int i = 0; i < actorsElements.length; ++i) {
                            String actor = actorsElements[i].getText().toString();
                            //Log.d("filmObject", "!!!!!!!!!!!!" + actor);
                            Matcher matcher = Pattern.compile(PATERN).matcher(actor);
                            if(matcher.matches()) {
                                filmObjectForDownload.addActor(actor);
                            }
                        }
                    }

                    if("Звук:".equals(str)) {
                        TagNode[] descriptionElements = liElements[q].getElementsByName("p", true);
                        String descr = descriptionElements[0].getText().toString();
                        descr = descr.replaceAll("&nbsp;", " ");
                        descr = descr.replaceAll("&mdash;", " ");
                        filmObjectForDownload.setDescription(descr);
                    }

                    /*
                    Matcher matcherPrem = Pattern.compile("^Премьера[\\sа-яА-Я:]+$").matcher(str);
                    if(matcherPrem.matches() && !findYear) {
                        String strDateWithElem = liElements[q].getText().toString();
                        Pattern p = Pattern.compile("(&nbsp;).+(\\1)");
                        Matcher m = p.matcher(strDateWithElem);
                        m.find();
                        String strDate = m.group();
                        strDate = strDate.replaceAll("&nbsp;", "");
                        strDate = strDate.replaceAll("^[0-9][0-9]\\.[0-9][0-9]\\.", "");
                        filmObjectForDownload.setYear(strDate);
                        findYear = true;
                    }
                    */

                }

                find = true;
            }
            if(find) {
                break;
            }

        }
        filmObjectForDownload.writeToLog();

        return filmObjectForDownload;
    }

}

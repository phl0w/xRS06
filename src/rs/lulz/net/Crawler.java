package rs.lulz.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

    public StringBuffer sb = new StringBuffer("");

    public Crawler(final String URL) {

        final BufferedReader URL_BUFFERED_READER;

        try {
            {
                final URL URL_LINK = new URL(URL);
                URL_BUFFERED_READER = new BufferedReader(new InputStreamReader(URL_LINK.openStream()));
            }
            String tmp = "";
            while ((tmp = URL_BUFFERED_READER.readLine()) != null) {
                sb.append(tmp);
                sb.append("\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public String getContents() {
        return sb.toString();
    }

    public StringBuffer getContentBuffer() {
        return sb;
    }

    public String find(final String REGEX, final int GROUP_INDEX) {

        final Pattern REGEX_PATTERN = Pattern.compile(REGEX);
        String s;

        BufferedReader br = new BufferedReader(new StringReader(sb.toString()));

        try {
            while ((s = br.readLine()) != null) {
                Matcher m = REGEX_PATTERN.matcher(s);

                if (m.find()) {
                    return m.group(GROUP_INDEX);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Crawler getCurrentInstance() {
        return this;
    }
}



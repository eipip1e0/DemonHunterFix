package com.facebook.stream;

import android.util.Log;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* access modifiers changed from: package-private */
public class StreamRenderer {
    private StringBuilder sb = new StringBuilder();

    public static String render(JSONObject data) {
        return new StreamRenderer().doRender(data);
    }

    public static String renderSinglePost(JSONObject post) throws JSONException {
        StreamRenderer renderer = new StreamRenderer();
        renderer.renderPost(post);
        return renderer.getResult();
    }

    public static String renderSingleComment(JSONObject comment) {
        StreamRenderer renderer = new StreamRenderer();
        renderer.renderComment(comment);
        return renderer.getResult();
    }

    private StreamRenderer() {
    }

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
    }

    private String getResult() {
        return this.sb.toString();
    }

    private String doRender(JSONObject data) {
        try {
            JSONArray posts = data.getJSONArray("data");
            append(new String[]{"<html><head>", "<link rel=\"stylesheet\" href=\"file:///android_asset/stream.css\" type=\"text/css\">", "<script src=\"file:///android_asset/stream.js\"></script>", "</head>", "<body>", "<div id=\"header\">"});
            renderLink("app://logout", "logout");
            renderStatusBox();
            append("<div id=\"posts\">");
            for (int i = 0; i < posts.length(); i++) {
                renderPost(posts.getJSONObject(i));
            }
            append("</div></body></html>");
            return getResult();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void renderStatusBox() {
        append(new String[]{"</div><div class=\"clear\"></div>", "<div id=\"status_box\">", "<input id=\"status_input\" value=\" What's on your mind?\"", " onfocus=\"onStatusBoxFocus(this);\"/>", "<button id=\"status_submit\" class=\"hidden\" onclick=\"updateStatus();\">Share</button>", "<div class=\"clear\"></div>", "</div>"});
    }

    private void renderPost(JSONObject post) throws JSONException {
        append("<div class=\"post\">");
        renderFrom(post);
        renderTo(post);
        renderMessage(post);
        renderAttachment(post);
        renderActionLinks(post);
        renderLikes(post);
        renderComments(post);
        renderCommentBox(post);
        append("</div>");
    }

    private void renderFrom(JSONObject post) throws JSONException {
        JSONObject from = post.getJSONObject("from");
        renderAuthor(from.getString("id"), from.getString("name"));
    }

    private void renderTo(JSONObject post) throws JSONException {
        JSONObject to = post.optJSONObject("to");
        if (to != null) {
            JSONObject toData = to.getJSONArray("data").getJSONObject(0);
            String toName = toData.getString("name");
            String toId = toData.getString("id");
            append(" > ");
            renderProfileLink(toId, toName);
        }
    }

    private void renderProfileLink(String id, String name) {
        renderLink(getProfileUrl(id), name);
    }

    private String getProfileUrl(String id) {
        return "http://touch.facebook.com/#/profile.php?id=" + id;
    }

    private void renderAuthor(String id, String name) {
        append(new String[]{"<div class=\"profile_pic_container\">", "<a href=\"", getProfileUrl(id), "\"><img class=\"profile_pic\" src=\"http://graph.facebook.com/", id, "/picture\"/></a>", "</div>"});
        renderProfileLink(id, name);
    }

    private void renderMessage(JSONObject post) {
        append(new String[]{"&nbsp;<span class=\"msg\">", post.optString("message"), "</span>", "<div class=\"clear\"></div>"});
    }

    private void renderAttachment(JSONObject post) {
        String name = post.optString("name");
        String link = post.optString("link");
        String picture = post.optString("picture");
        String source = post.optString("source");
        String caption = post.optString("caption");
        String description = post.optString("description");
        String[] fields = {name, link, picture, source, caption, description};
        boolean hasAttachment = false;
        int length = fields.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (fields[i].length() != 0) {
                hasAttachment = true;
                break;
            } else {
                i++;
            }
        }
        if (hasAttachment) {
            append("<div class=\"attachment\">");
            if (name != "") {
                append("<div class=\"title\">");
                if (link != null) {
                    renderLink(link, name);
                } else {
                    append(name);
                }
                append("</div>");
            }
            if (caption != "") {
                append("<div class=\"caption\">" + caption + "</div>");
            }
            if (picture != "") {
                append("<div class=\"picture\">");
                String img = "<img src=\"" + picture + "\"/>";
                if (link != "") {
                    renderLink(link, img);
                } else {
                    append(img);
                }
                append("</div>");
            }
            if (description != "") {
                append("<div class=\"description\">" + description + "</div>");
            }
            append("<div class=\"clear\"></div></div>");
        }
    }

    private void renderLink(String href, String text) {
        append(new String[]{"<a href=\"", href, "\">", text, "</a>"});
    }

    private void renderActionLinks(JSONObject post) {
        HashSet<String> actions = getActions(post);
        append("<div class=\"action_links\">");
        append("<div class=\"action_link\">");
        renderTimeStamp(post);
        append("</div>");
        String post_id = post.optString("id");
        if (actions.contains("Comment")) {
            renderActionLink(post_id, "Comment", "comment");
        }
        boolean canLike = actions.contains("Like");
        renderActionLink(post_id, "Like", "like", canLike);
        renderActionLink(post_id, "Unlike", "unlike", !canLike);
        append("<div class=\"clear\"></div></div>");
    }

    private void renderActionLink(String post_id, String title, String func) {
        renderActionLink(post_id, title, func, true);
    }

    private void renderActionLink(String post_id, String title, String func, boolean visible) {
        append(new String[]{"<div id=\"", func, post_id, "\" class=\"action_link ", visible ? "" : "hidden", "\">", "<a href=\"#\" onclick=\"", func, "('", post_id, "'); return false;\">", title, "</a></div>"});
    }

    private void renderTimeStamp(JSONObject post) {
        long num;
        String friendly;
        long seconds = (new Date().getTime() - getDateFormat().parse(post.optString("created_time"), new ParsePosition(0)).getTime()) / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        if (days > 0) {
            num = days;
            friendly = String.valueOf(days) + " day";
        } else if (hours > 0) {
            num = hours;
            friendly = String.valueOf(hours) + " hour";
        } else if (minutes > 0) {
            num = minutes;
            friendly = String.valueOf(minutes) + " minute";
        } else {
            num = seconds;
            friendly = String.valueOf(seconds) + " second";
        }
        if (num > 1) {
            friendly = String.valueOf(friendly) + "s";
        }
        append(new String[]{"<div class=\"timestamp\">", friendly, " ago", "</div>"});
    }

    private HashSet<String> getActions(JSONObject post) {
        HashSet<String> actionsSet = new HashSet<>();
        JSONArray actions = post.optJSONArray("actions");
        if (actions != null) {
            for (int j = 0; j < actions.length(); j++) {
                actionsSet.add(actions.optJSONObject(j).optString("name"));
            }
        }
        return actionsSet;
    }

    private void renderLikes(JSONObject post) {
        String desc;
        int numLikes = post.optInt("likes", 0);
        if (numLikes > 0) {
            if (numLikes == 1) {
                desc = "person likes this";
            } else {
                desc = "people like this";
            }
            append(new String[]{"<div class=\"like_icon\">", "<img src=\"file:///android_asset/like_icon.png\"/>", "</div>", "<div class=\"num_likes\">", new Integer(numLikes).toString(), " ", desc, "</div>"});
        }
    }

    private void renderComments(JSONObject post) throws JSONException {
        JSONArray data;
        append("<div class=\"comments\" id=\"comments" + post.optString("id") + "\">");
        JSONObject comments = post.optJSONObject("comments");
        if (!(comments == null || (data = comments.optJSONArray("data")) == null)) {
            for (int j = 0; j < data.length(); j++) {
                renderComment(data.getJSONObject(j));
            }
        }
        append("</div>");
    }

    private void renderComment(JSONObject comment) {
        JSONObject from = comment.optJSONObject("from");
        if (from == null) {
            Log.w("StreamRenderer", "Comment missing from field: " + comment.toString());
        } else {
            renderAuthor(from.optString("id"), from.optString("name"));
        }
        String message = comment.optString("message");
        append("<div class=\"comment\">");
        append(new String[]{"&nbsp;", message, "</div>"});
    }

    private void renderCommentBox(JSONObject post) {
        String id = post.optString("id");
        append(new String[]{"<div class=\"comment_box\" id=\"comment_box", id, "\">", "<input id=\"comment_box_input", id, "\"/>", "<button onclick=\"postComment('", id, "');\">Post</button>", "<div class=\"clear\"></div>", "</div>"});
    }

    private void append(String str) {
        this.sb.append(str);
    }

    private void append(String[] chunks) {
        for (String chunk : chunks) {
            this.sb.append(chunk);
        }
    }
}

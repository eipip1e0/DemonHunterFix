package com.facebook.stream;

import android.os.Bundle;
import com.facebook.android.AsyncFacebookRunner;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class StreamJsHandler {
    private final StreamHandler streamHandler;

    StreamJsHandler(StreamHandler streamHandler2) {
        this.streamHandler = streamHandler2;
    }

    private AsyncFacebookRunner getFb() {
        return new AsyncFacebookRunner(Session.restore(this.streamHandler.getActivity()).getFb());
    }

    public void updateStatus(final String message) {
        AsyncFacebookRunner fb = getFb();
        Bundle params = new Bundle();
        params.putString("message", message);
        fb.request("me/feed", params, "POST", new AsyncRequestListener() {
            /* class com.facebook.stream.StreamJsHandler.AnonymousClass1 */

            @Override // com.facebook.stream.AsyncRequestListener
            public void onComplete(JSONObject obj) {
                try {
                    StreamJsHandler.this.callJs("onStatusUpdated('" + StreamJsHandler.this.renderStatus(obj, message).replace("'", "\\'") + "');");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String renderStatus(JSONObject response, String message) throws JSONException {
        String postId = response.getString("id");
        JSONObject post = new JSONObject();
        post.put("id", postId);
        post.put("message", message);
        post.put("from", createAuthorObj());
        JSONArray actions = new JSONArray();
        JSONObject like = new JSONObject();
        like.put("name", "Like");
        actions.put(like);
        JSONObject comment = new JSONObject();
        comment.put("name", "Comment");
        actions.put(comment);
        post.put("actions", actions);
        post.put("created_time", StreamRenderer.getDateFormat().format(new Date()));
        return StreamRenderer.renderSinglePost(post);
    }

    public void like(final String post_id, final boolean val) {
        Bundle params = new Bundle();
        if (!val) {
            params.putString("method", "delete");
        }
        getFb().request(String.valueOf(post_id) + "/likes", new Bundle(), "POST", new AsyncRequestListener() {
            /* class com.facebook.stream.StreamJsHandler.AnonymousClass2 */

            @Override // com.facebook.stream.AsyncRequestListener
            public void onComplete(JSONObject response) {
                StreamJsHandler.this.callJs("javascript:onLike('" + post_id + "'," + val + ")");
            }
        });
    }

    public void postComment(final String post_id, final String message) {
        Bundle params = new Bundle();
        params.putString("message", message);
        getFb().request(String.valueOf(post_id) + "/comments", params, "POST", new AsyncRequestListener() {
            /* class com.facebook.stream.StreamJsHandler.AnonymousClass3 */

            @Override // com.facebook.stream.AsyncRequestListener
            public void onComplete(JSONObject response) {
                try {
                    StreamJsHandler.this.callJs("onComment('" + post_id + "','" + StreamJsHandler.this.renderComment(response, message).replace("'", "\\'") + "');");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String renderComment(JSONObject response, String message) throws JSONException {
        JSONObject comment = new JSONObject();
        comment.put("id", response.getString("id"));
        comment.put("from", createAuthorObj());
        comment.put("message", message);
        return StreamRenderer.renderSingleComment(comment);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void callJs(String js) {
        this.streamHandler.getWebView().loadUrl("javascript:" + js);
    }

    private JSONObject createAuthorObj() throws JSONException {
        Session session = Session.restore(this.streamHandler.getActivity());
        JSONObject from = new JSONObject();
        from.put("id", session.getUid());
        from.put("name", session.getName());
        return from;
    }
}

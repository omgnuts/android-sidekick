//package com.jattcode.common;
//
///**
// * Created by Javan on 5/7/2015.
// */
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.ActivityNotFoundException;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.Uri;
//
//import com.jattcode.mikimedia.R;
//
//public class RateMaker {
//
//    private static final String RATE_PREFERENCES = "RATE_PREFERENCES";
//
//    private static RateMaker singleton;
//
//    public static synchronized RateMaker get(Context context) {
//        if (singleton == null) {
//            singleton = new RateMaker(context);
//        }
//        return singleton;
//    }
//
//    public enum RateEvent {
//        RESUME_APPLICATION,
//        PAUSE_APPLICATION,
//        ATTEMPTED_SEARCH,
//        SUCCESSFUL_SEARCH,
//    }
//
//    private static final long RATING_TIMER = 1000 * 60 * 15; // 15 mins
//
//    private static final int SUCCESS_HITS = 50;
//
//    private final Context context;
//
//    private final SharedPreferences preferences;
//
//    private long startTime;
//
//    private RateMaker(final Context context) {
//        this.context = context;
//        this.startTime = System.currentTimeMillis();
//        preferences = context.getSharedPreferences(
//                RATE_PREFERENCES, Context.MODE_PRIVATE);
//    }
//
//    public boolean hasRated() {
//        return preferences.getBoolean("HAS_RATED", false);
//    }
//
//    private void setHasRated(int level) {
//        preferences.edit().putBoolean("HAS_RATED", true).putInt("RATE_LEVEL", level).commit();
//    }
//
//    private void skipAgain(boolean algorithm) {
//        final SharedPreferences.Editor editor = preferences.edit();
//
//        if (algorithm) editor.putInt("USER_SAYS_LATER", preferences.getInt("USER_SAYS_LATER", 0) + 1);
//        editor.putLong("USAGE_TIME", 0);
//        editor.putInt("SUCCESS_HITS", 0);
//        editor.putInt("ATTEMPT_HITS", 0);
//
//        editor.apply();
//    }
//
//    public void trackEvent(RateEvent event) {
//
//        if (hasRated()) return;
//
//        final SharedPreferences.Editor editor = preferences.edit();
//
//        long usage = preferences.getLong("USAGE_TIME", 0);
//        int success = preferences.getInt("SUCCESS_HITS", 0);
//        int attempts = preferences.getInt("ATTEMPT_HITS", 0);
//
//        switch (event) {
//
//            case RESUME_APPLICATION:
//                startTime = System.currentTimeMillis();
//                return;
//            case PAUSE_APPLICATION:
//                long endTime = System.currentTimeMillis();
//                editor.putLong("USAGE_TIME", usage + endTime - startTime);
//                editor.commit();
//                return;
//            case ATTEMPTED_SEARCH:
//                editor.putInt("ATTEMPT_HITS", ++attempts);
//                editor.commit();
//                return;
//            case SUCCESSFUL_SEARCH:
//                editor.putInt("SUCCESS_HITS", ++success);
//                editor.commit();
//                break;
//
//            default:
//                break;
//        }
//
//        int later = Math.min(5, preferences.getInt("USER_SAYS_LATER", 0));
//
//        if (usage > (RATING_TIMER - later * 60000 * 2) || // every LATER reduces time by 2mins
//                success >= (SUCCESS_HITS - later)) { // every LATER reduces success reqt by 1
//
////            String s = "usage = " + usage + "\n"
////                    + " > usage-cond = " + (RATING_TIMER - later * 60000 * 2) + "\n"
////                    + " success = " + success + "\n"
////                    + " >= succ-cond = " +(SUCCESS_HITS - later);
////
////            System.out.println("....................");
////            System.out.println(s);
//            skipAgain(false);
////			rateTheBugger(true, null);
//        }
//    }
//
//    // rated from the preference i.e. not from being tracked
//    public void rateTheBugger(OnRatedListener listener) {
//        rateTheBugger(false, listener);
//    }
//
//    private void rateTheBugger(final boolean algorithm, final OnRatedListener listener) {
//
//        final String[] ratings = context.getResources().getStringArray(R.array.ratemaker_options);
//
//        // Creating and Building the Dialog
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//        builder .setTitle(R.string.ratemaker_title)
//                .setIcon(R.mipmap.ic_ratemaker_like)
//                .setPositiveButton(R.string.ratemaker_skip, new OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dlg, int item) {
//                        skipAgain(algorithm); // skip for later was pressed
//                        dlg.dismiss();
//                    }
//                })
//                .setSingleChoiceItems(ratings, -1, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int reverseLevel) {
//
//                        final AlertDialog.Builder b = new AlertDialog.Builder(context);
//                        final boolean goPlaystore;
//                        switch (reverseLevel) {
//                            case 0:
//                            case 1:
//                                b.setMessage(R.string.ratemaker_good_thanks);
//                                goPlaystore = true;
//                                break;
//                            default:
//                                b.setMessage(R.string.ratemaker_bad_thanks);
//                                goPlaystore = false;
//                                break;
//                        }
//
//                        b.setPositiveButton(R.string.ratemaker_okay, new OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface d, int i) {
//                                if (goPlaystore) sendPlaystoreIntent();
//                                d.dismiss();
//                            }
//                        });
//                        b.create().show();
//
//                        setHasRated(5 - reverseLevel);
//                        if (listener != null) listener.hasPerformedRating();
//                        dialog.dismiss();
//                    }
//                });
//
//        Dialog dialog = builder.create();
//        dialog.show();
//    }
//
//    private void sendPlaystoreIntent() {
//
//        Uri uri = Uri.parse(getPlaystoreAppLink(false));
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        try {
//            context.startActivity(intent);
//        } catch (ActivityNotFoundException e) { // browser on fallback
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getPlaystoreAppLink(true))));
//        }
//    }
//
//    public String getPlaystoreAppLink(boolean browser) {
//        String packageName = "com.jattcode.mikimedia"; //getActivity().getPackageName();
//        if (browser) return "http://play.google.com/store/apps/details?id=" + packageName;
//        return "market://details?id=" + packageName;
//    }
//
//
//    public void sendShareAppIntent() {
//        String title = "Share this app!";
//        String subject = "Browse BackPage.com on mobile comfort :D";
//        String msg = "Check this app out: \n\n" +
//                "Browse Backpage.com classifieds so fast and furiously. \n" +
//                getPlaystoreAppLink(true) + "\n" +
//                "- Enjoy~" ;
//
//        Intent share = new Intent(android.content.Intent.ACTION_SEND);
//        share.setType("text/plain");
//        share.putExtra(Intent.EXTRA_SUBJECT, subject);
//        share.putExtra(Intent.EXTRA_TEXT, msg);
//        share.setAction(Intent.ACTION_SEND);
//        context.startActivity(Intent.createChooser(share, title));
//    }
//
//    public void sendTwitterIntent() {
//        String url = "https://twitter.com/jimcoven";
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(url));
//        context.startActivity(intent);
//    }
//
//    public void sendJattcodeIntent() {
//        String url = "http://www.jattcode.com";
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(url));
//        context.startActivity(intent);
//    }
//
//    public interface OnRatedListener {
//        void hasPerformedRating();
//    }
//
//}

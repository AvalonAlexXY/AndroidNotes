package com.uuloop.adclient.manager;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.renderscript.Sampler;

import com.uuloop.adclient.AdClientApplication;
import com.uuloop.adclient.util.FileUtil;
import com.uuloop.adclient.util.Logger;
import com.uuloop.adclient.util.TimeUtil;

import java.io.RandomAccessFile;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitorManager implements Runnable {

    private static final String TAG = "MonitorManager";

    private volatile static MonitorManager instance = null;

    private final String MONITOR_LOG_FILENAME = "monitor.log";

    private ScheduledExecutorService scheduler = null;
    private ActivityManager activityManager = null;
    private SysParamManager mSysParamManager = null;
    private Context mContext = null;
    private Logger mLogger = null;

    private String mLogPath = null;

    private long freq = 0L;
    private Long lastCpuTime = 0L;
    private Long lastAppCpuTime = 0L;

    private RandomAccessFile procStatFile = null;
    private RandomAccessFile appStatFile = null;

    private MonitorManager() {
        mContext = AdClientApplication.getInstance();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        mSysParamManager = SysParamManager.getInstance();
        mLogger = new Logger();
        mLogPath = mSysParamManager.getAppLogDir();
        FileUtil.createDir(mLogPath);
    }

    public static MonitorManager getInstance() {
        if (instance == null) {
            synchronized (Sampler.class) {
                if (instance == null) {
                    instance = new MonitorManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context, long freq) {
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        this.freq = freq;
    }

    public void start() {
        scheduler.scheduleWithFixedDelay(this, 0L, freq, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        String logFullName = mLogPath + TimeUtil.getFormatCurrentDay() + "_" + MONITOR_LOG_FILENAME;
        FileUtil.WriteFile(logFullName, TimeUtil.getFormatCurrentDate() + ": memory:" + memory() + "M ,cpu: " + appCPU(getProcStat()) + "%\n");
    }

    private float appCPU(String[] procStats) {
        float sampleValue = 0.0F;
        long appTime = getAppStatCPUTime(getMyPid());
        long cpuTime = Long.parseLong(procStats[2]) + Long.parseLong(procStats[3])
                + Long.parseLong(procStats[4]) + Long.parseLong(procStats[5])
                + Long.parseLong(procStats[6]) + Long.parseLong(procStats[7])
                + Long.parseLong(procStats[8]);
        if (lastCpuTime == 0L && lastAppCpuTime == 0L) {
            lastCpuTime = cpuTime;
            lastAppCpuTime = appTime;
            return sampleValue;
        }
        sampleValue = ((float) (appTime - lastAppCpuTime) / (float) (cpuTime - lastCpuTime)) * 100F;
        lastCpuTime = cpuTime;
        lastAppCpuTime = appTime;
        return sampleValue;
    }

    private float memory() {
        float mem = 0.0F;
        try {
            final Debug.MemoryInfo[] memInfo = activityManager.getProcessMemoryInfo(new int[]{android.os.Process.myPid()});
            if (memInfo.length > 0) {
                final int totalPss = memInfo[0].getTotalPss();
                if (totalPss >= 0) {
                    mem = (float) (totalPss / 1024.0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mem;
    }

    private String[] getProcStat() {
        String[] procStats = null;
        try {
            if (procStatFile == null) {
                procStatFile = new RandomAccessFile("/proc/stat", "r");
            } else {
                procStatFile.seek(0L);
            }
            String procStatString = procStatFile.readLine();
            procStats = procStatString.split(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procStats;
    }

    private long getAppStatCPUTime(int pid) {
        long appCpuTime = 0L;
        try {
            if (appStatFile == null) {
                appStatFile = new RandomAccessFile("/proc/" + pid + "/stat", "r");
            } else {
                appStatFile.seek(0L);
            }
            String appStatString = appStatFile.readLine();
            String[] appStats = appStatString.split(" ");
            appCpuTime = Long.parseLong(appStats[13]) + Long.parseLong(appStats[14]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appCpuTime;
    }

    private int getPid(String packagename) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> mRunningProcess = am.getRunningAppProcesses();
        int pid = -1;
        for (ActivityManager.RunningAppProcessInfo amProcess : mRunningProcess) {
            mLogger.i(TAG, "process name : " + amProcess.processName);
            if (amProcess.processName.equals(packagename)) {
                pid = amProcess.pid;
                break;
            }
        }
        return pid;
    }

    private int getMyPid() {
        return android.os.Process.myPid();
    }

}

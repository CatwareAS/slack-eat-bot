package com.catware.eatapp.slack.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KeepAliveJob {

    private static final Logger log = LoggerFactory.getLogger(KeepAliveJob.class);

    @Scheduled(cron = "${slack.jobs.keep-alive}", zone = "Europe/Kiev")
    public void keepAlive() {
        log.info("Keeping service active");
    }
}
package ru.vasire.mytestengine;

public class TestStatistic {
    private int passed;
    private int failed;

    public int getCount() {
        return passed + failed;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }

    public void addPassedTest(){
        this.passed++;
    }

    public void addFailedTest(){
        this.failed++;
    }

    @Override
    public String toString() {
        return String.format("Test statistic: test count: %s, Passed: %s, Failed: %s", getCount(), getPassed(), getFailed());
    }
}

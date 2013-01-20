package rs.lulz.bot.loader.asm.analysis.math;

public class ExtendedMath {

    public double stdDevPop(final int datum, final int mean, final int numOfData) {
        return Math.sqrt((datum - mean) / (numOfData));
    }

    public double stdDevSample(final int datum, final int mean, final int numOfData) {
        return Math.sqrt((datum - mean) / (numOfData - 1));
    }
}

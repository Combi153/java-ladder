package laddergame.domain.ladder;

import laddergame.util.BooleanGenerator;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private static final boolean DOES_NOT_EXIST = false;
    private static final int MINIMUM_INDEX = 0;
    private static final int ORDER_UNIT = 1;

    private final List<Rung> rungs;
    private final BooleanGenerator randomBooleanGenerator;

    private Line(final int rungCount, final BooleanGenerator randomBooleanGenerator) {
        this.randomBooleanGenerator = randomBooleanGenerator;
        rungs = makeRungs(rungCount);
    }

    public static Line create(final int rungCount, final BooleanGenerator rungBooleanGenerator) {
        return new Line(rungCount, rungBooleanGenerator);
    }

    public boolean hasRung(final int index) {
        if (index < MINIMUM_INDEX || index >= rungs.size()) {
            return DOES_NOT_EXIST;
        }
        Rung rung = rungs.get(index);
        return rung.exists();
    }

    private List<Rung> makeRungs(final int rungCount) {
        List<Rung> rungs = new ArrayList<>();
        Rung firstRung = Rung.create(randomBooleanGenerator.generate());
        rungs.add(firstRung);

        for (int order = 1; order < rungCount; order++) {
            Rung previousRung = rungs.get(order - ORDER_UNIT);
            Rung newRung = Rung.create(generateWith(previousRung));
            rungs.add(newRung);
        }
        return rungs;
    }

    private boolean generateWith(final Rung previousRung) {
        if (previousRung.exists()) {
            return DOES_NOT_EXIST;
        }
        return randomBooleanGenerator.generate();
    }

    public List<Rung> getRungs() {
        return rungs;
    }
}

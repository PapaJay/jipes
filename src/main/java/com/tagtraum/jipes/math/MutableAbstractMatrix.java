/*
 * =================================================
 * Copyright 2013 tagtraum industries incorporated
 * All rights reserved.
 * =================================================
 */
package com.tagtraum.jipes.math;

/**
 * Mutable {@link AbstractMatrix}.
 *
 * @author <a href="mailto:hs@tagtraum.com">Hendrik Schreiber</a>
 */
public abstract class MutableAbstractMatrix extends AbstractMatrix implements MutableMatrix {

    /**
     * Calls {@link MatrixBackingBuffer#allocate(int)} on the used buffer.
     *
     * @param buffer buffer
     */
    protected abstract void allocate(final MatrixBackingBuffer buffer);

    @Override
    public void copy(final Matrix fromMatrix) {
        final int rows = Math.min(this.rows, fromMatrix.getNumberOfRows());
        final int columns = Math.min(this.columns, fromMatrix.getNumberOfColumns());
        copy(fromMatrix, 0, 0, 0, 0, rows, columns);
    }

    @Override
    public void copy(final Matrix fromMatrix, final int fromRow, final int fromColumn,
                     final int toRow, final int toColumn,
                     final int rows, final int columns) {
        copy(fromMatrix, fromRow, fromColumn, this, toRow, toColumn, rows, columns);
    }

    public static void copy(final Matrix fromMatrix, final int fromRow, final int fromColumn,
                            final MutableMatrix toMatrix, final int toRow, final int toColumn,
                            final int rows, final int columns) {
        for (int currentFromRow = fromRow, currentToRow = toRow; currentFromRow<fromRow+rows; currentFromRow++, currentToRow++) {
            for (int currentFromColumn = fromColumn, currentToColumn = toColumn; currentFromColumn<fromColumn+columns; currentFromColumn++, currentToColumn++) {
                toMatrix.set(currentToRow, currentToColumn, fromMatrix.get(currentFromRow, currentFromColumn));
            }
        }
    }

    public void set(final int row, final int column, final float value) {
        checkBounds(row, column);
        set(toIndex(row, column), value);
    }


    protected void set(final int index, final float value) {
        buffer.set(index, value);
    }

}

/*
 * =================================================
 * Copyright 2013 tagtraum industries incorporated
 * All rights reserved.
 * =================================================
 */
package com.tagtraum.jipes.math;

/**
 * Matrix for data that is mirrored along the diagonal.
 * Any data set in one half of the matrix is automatically set in the other half
 * without doubling memory consumption.
 *
 * @author <a href="mailto:hs@tagtraum.com">Hendrik Schreiber</a>
 */
public class SymmetricMatrix extends MutableAbstractMatrix implements MutableMatrix {

    /**
     * Creates a square, symmetric matrix with the given number of rows and columns.
     * Memory is allocated from the Java heap. The matrix is <em>not</em> zero-padded.
     *
     * @param length rows == columns (square)
     */
    public SymmetricMatrix(final int length) {
        this(length, false, false);
    }

    /**
     * Creates a square, symmetric matrix with the given number of rows and columns.
     * Memory is allocated using the given backing buffer. The matrix may be zero-padded.
     *
     * @param buffer backing buffer
     * @param length rows == columns (square)
     * @param zeroPadded zero padded?
     */
    public SymmetricMatrix(final int length, final MatrixBackingBuffer buffer, final boolean zeroPadded) {
        this(length, buffer, zeroPadded, true);
    }

    /**
     * Creates a square, symmetric matrix with the given number of rows and columns.
     * Memory is allocated using a {@link FloatBackingBuffer}. The matrix may be zero-padded.
     *
     * @param length rows == columns (square)
     * @param direct allocate the backing buffer directly/natively or on the Java heap
     * @param zeroPadded zero padded?
     */
    public SymmetricMatrix(final int length, final boolean direct, final boolean zeroPadded) {
        this(length, new FloatBackingBuffer(direct), zeroPadded);
    }

    /**
     * Creates a square, symmetric matrix with the data from the given matrix.
     * Memory is allocated using a {@link FloatBackingBuffer}. The matrix may be zero-padded.
     *
     * @param matrix matrix
     * @param buffer backing buffer
     * @param zeroPadded zero padded?
     */
    public SymmetricMatrix(final Matrix matrix, final MatrixBackingBuffer buffer, final boolean zeroPadded) {
        this(matrix.getNumberOfRows(), buffer, zeroPadded, true);
        copy(matrix);
    }

    protected SymmetricMatrix(final int length, final MatrixBackingBuffer buffer, final boolean zeroPadded, final boolean allocate) {
        this.buffer = buffer;
        this.rows = length;
        this.columns = length;
        this.zeroPadded = zeroPadded;
        if (allocate) {
            allocate(buffer);
        }
    }

    @Override
    protected void allocate(final MatrixBackingBuffer buffer) {
        buffer.allocate((rows*(rows+1))/2);
    }

    @Override
    public float get(final int row, final int column) {
        if (row > column) {
            return get(column, row);
        } else {
            return super.get(row, column);
        }
    }

    @Override
    public void set(final int row, final int column, final float value) {
        if (row > column) {
            set(column, row, value);
        } else {
            super.set(row, column, value);
        }
    }

    @Override
    protected int toIndex(final int row, final int column) {
        final int regularIndex = row*rows+column;
        final int correction = (row*(row+1))/2;
        return regularIndex - correction;
    }

}

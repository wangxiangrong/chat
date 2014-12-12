package ru.ifmo.ctddev.koval.chat.model.util;

import javax.annotation.Nonnull;

public interface Visitable<I extends Visitable<I, V>, V extends Visitor<I, V>> {
    void visitBy(@Nonnull V visitor);
}
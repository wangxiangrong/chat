package ru.ifmo.ctddev.koval.chat.model.util;

import javax.annotation.Nonnull;

public interface Visitor<I extends Visitable<I, V>, V extends Visitor<I, V>> {
    void visitDefault(@Nonnull I item);
}
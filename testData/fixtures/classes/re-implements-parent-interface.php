<?php

namespace BasicCasesHolder {
    interface ParentInterface                                             {}
    interface ChildInterface extends ParentInterface                      {}
    abstract class AbstractOne implements ParentInterface                 {}
    abstract class AbstractTwo implements ParentInterface, ChildInterface {}

    class ClassOne extends AbstractOne
        implements
            <warning descr="'\BasicCasesHolder\ParentInterface' is already announced in '\BasicCasesHolder\AbstractOne'.">ParentInterface</warning> {}

    class ClassTwo extends AbstractTwo
        implements
            <warning descr="'\BasicCasesHolder\ParentInterface' is already announced in '\BasicCasesHolder\AbstractTwo'.">ParentInterface</warning>,
            <warning descr="'\BasicCasesHolder\ChildInterface' is already announced in '\BasicCasesHolder\AbstractTwo'.">ChildInterface</warning> {}
}

namespace AliasingCasesHolder {
    use \Traversable as ForeachSupport;

    abstract class AbstractClass implements \Traversable {}
    class RegularClass extends AbstractClass
        implements
            <warning descr="'\Traversable' is already announced in '\AliasingCasesHolder\AbstractClass'.">ForeachSupport</warning> {}
}
/*
 * Copyright 2012 Fabio Strozzi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// LogGrammar.java, created on Apr 18, 2012
package eu.fabiostrozzi.ceppo;

import static eu.fabiostrozzi.ceppo.Utils.lastOrAppendNew;
import eu.fabiostrozzi.ceppo.lang.ClosureStatement;
import eu.fabiostrozzi.ceppo.lang.ConstraintStatement;
import eu.fabiostrozzi.ceppo.lang.ContextStatement;
import eu.fabiostrozzi.ceppo.lang.DirectionStatement;
import eu.fabiostrozzi.ceppo.lang.EntityStatement;
import eu.fabiostrozzi.ceppo.lang.EventStatement;
import eu.fabiostrozzi.ceppo.lang.ExpectedStatement;
import eu.fabiostrozzi.ceppo.lang.FoundStatement;
import eu.fabiostrozzi.ceppo.lang.MessageStatement;
import eu.fabiostrozzi.ceppo.lang.SubjectStatement;
import eu.fabiostrozzi.ceppo.lang.TellerStatement;
import eu.fabiostrozzi.ceppo.lang.UserStatement;
import eu.fabiostrozzi.ceppo.terms.Constraint;
import eu.fabiostrozzi.ceppo.terms.Context;
import eu.fabiostrozzi.ceppo.terms.Entity;
import eu.fabiostrozzi.ceppo.terms.ExpectedAndFound;
import eu.fabiostrozzi.ceppo.terms.Happening;
import eu.fabiostrozzi.ceppo.terms.Message;
import eu.fabiostrozzi.ceppo.terms.Teller;
import eu.fabiostrozzi.ceppo.terms.User;

/**
 * The log language grammar.
 * 
 * @author fabio
 */
public class LogGrammar extends BaseGrammar implements ClosureStatement, ConstraintStatement,
        ContextStatement, EventStatement, ExpectedStatement, FoundStatement, MessageStatement,
        SubjectStatement, TellerStatement, UserStatement, EntityStatement {

    /**
     * Constructor requires a log level.
     * 
     * @param level
     *            The log level.
     */
    public LogGrammar(CeppoLevel level) {
        super(level);
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.TellerStatement#when(java.lang.String)
     */
    public SubjectStatement when(String when) {
        Teller t = new Teller();
        t.setWhen(when);
        terms.add(t);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.TellerStatement#when(java.lang.String, java.lang.Object[])
     */
    public SubjectStatement when(String format, Object[] params) {
        Teller t = new Teller();
        t.setWhen(format);
        t.setWhenParams(params);
        terms.add(t);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.TellerStatement#during(java.lang.String)
     */
    public SubjectStatement during(String when) {
        Teller t = new Teller();
        t.setWhen(when);
        terms.add(t);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.TellerStatement#during(java.lang.String, java.lang.Object[])
     */
    public SubjectStatement during(String format, Object[] params) {
        Teller t = new Teller();
        t.setWhen(format);
        t.setWhenParams(params);
        terms.add(t);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.SubjectStatement#message(java.lang.String)
     */
    public MessageStatement message(String type) {
        Message m = new Message();
        m.setType(type);
        terms.add(m);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.SubjectStatement#constraint(java.lang.String)
     */
    public ConstraintStatement constraint(String constraint) {
        Constraint c = new Constraint();
        c.setName(constraint);
        terms.add(c);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.SubjectStatement#occurred(java.lang.String)
     */
    public ClosureStatement occurred(String what) {
        Happening s = new Happening();
        s.setWhat(what);
        terms.add(s);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.SubjectStatement#occurred(java.lang.String,
     * java.lang.Object[])
     */
    public ClosureStatement occurred(String format, Object... params) {
        Happening s = new Happening();
        s.setWhat(format);
        s.setParams(params);
        terms.add(s);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.SubjectStatement#occurred(java.lang.Throwable)
     */
    public ClosureStatement occurred(Throwable throwable) {
        Happening s = new Happening();
        s.setException(throwable);
        terms.add(s);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.MessageStatement#from(java.lang.String)
     */
    public EventStatement from(String from) {
        Message m = lastOrAppendNew(terms, Message.class);
        m.setFrom(from);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.MessageStatement#to(java.lang.String)
     */
    public EventStatement to(String to) {
        Message m = lastOrAppendNew(terms, Message.class);
        m.setTo(to);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.FoundStatement#found(java.lang.Object[])
     */
    public ClosureStatement found(Object... what) {
        ExpectedAndFound ef = lastOrAppendNew(terms, ExpectedAndFound.class);
        ef.setFound(what);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.ExpectedStatement#expected(java.lang.Object[])
     */
    public FoundStatement expected(Object... what) {
        ExpectedAndFound ef = new ExpectedAndFound();
        ef.setExpected(what);
        terms.add(ef);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.EventStatement#failed()
     */
    public ContextStatement failed() {
        Message m = lastOrAppendNew(terms, Message.class);
        m.setSucceeded(false);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.EventStatement#failed(java.lang.String)
     */
    public ContextStatement failed(String why) {
        Message m = lastOrAppendNew(terms, Message.class);
        m.setSucceeded(false);
        m.setWhy(why);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.EventStatement#succeeded()
     */
    public ContextStatement succeeded() {
        Message m = lastOrAppendNew(terms, Message.class);
        m.setSucceeded(true);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.EventStatement#succeeded(java.lang.String)
     */
    public ContextStatement succeeded(String why) {
        Message m = lastOrAppendNew(terms, Message.class);
        m.setSucceeded(true);
        m.setWhy(why);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.EventStatement#prepared()
     */
    public ContextStatement prepared() {
        Message m = lastOrAppendNew(terms, Message.class);
        m.setPrepared(true);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.ContextStatement#with(java.lang.Object[])
     */
    public ClosureStatement with(Object... attributes) {
        Context c = new Context();
        c.setAttributes(attributes);
        terms.add(c);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.ConstraintStatement#passed()
     */
    public ExpectedStatement passed() {
        Constraint c = lastOrAppendNew(terms, Constraint.class);
        c.setPassed(true);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.ConstraintStatement#passed(java.lang.String)
     */
    public ExpectedStatement passed(String how) {
        Constraint c = lastOrAppendNew(terms, Constraint.class);
        c.setPassed(true);
        c.setPassedHow(how);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.ConstraintStatement#violated()
     */
    public ExpectedStatement violated() {
        Constraint c = lastOrAppendNew(terms, Constraint.class);
        c.setPassed(false);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.ConstraintStatement#violated(java.lang.String)
     */
    public ExpectedStatement violated(String how) {
        Constraint c = lastOrAppendNew(terms, Constraint.class);
        c.setPassed(false);
        c.setPassedHow(how);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.MessageStatement#content(java.lang.String)
     */
    @Override
    public DirectionStatement content(String content) {
        Message m = lastOrAppendNew(terms, Message.class);
        m.setContent(content);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.SubjectStatement#user(java.lang.String)
     */
    @Override
    public UserStatement user(String username) {
        User u = new User();
        u.setUsername(username);
        terms.add(u);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.User#created()
     */
    @Override
    public ContextStatement created() {
        User u = lastOrAppendNew(terms, User.class);
        u.setCreated(true);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.User#deleted()
     */
    @Override
    public ContextStatement deleted() {
        User u = Utils.lastOrAppendNew(terms, User.class);
        u.setDeleted(true);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.User#signedIn()
     */
    @Override
    public ContextStatement signedIn() {
        User u = lastOrAppendNew(terms, User.class);
        u.setSignedIn(true);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.User#signedOut()
     */
    @Override
    public ContextStatement signedOut() {
        User u = lastOrAppendNew(terms, User.class);
        u.setSignedOut(true);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.SubjectStatement#entity(java.lang.String)
     */
    @Override
    public EntityStatement entity(String entity) {
        Entity e = new Entity();
        e.setName(entity);
        terms.add(e);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.lang.EntityStatement#did(java.lang.String)
     */
    @Override
    public ContextStatement did(String what) {
        Entity e = lastOrAppendNew(terms, Entity.class);
        e.setAction(what);
        return this;
    }
}

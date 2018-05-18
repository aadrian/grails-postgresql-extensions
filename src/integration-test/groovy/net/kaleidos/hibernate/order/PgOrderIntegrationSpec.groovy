package net.kaleidos.hibernate.order

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Specification
import test.json.TestMapJsonb

@Integration
@Rollback
class PgOrderIntegrationSpec extends Specification {

    def pgOrderService

    def setup() {
        TestMapJsonb.executeUpdate('delete from TestMapJsonb')
    }

    void 'Order by a json property'() {
        setup:
            new TestMapJsonb(data: [name: 'Iván', lastName: 'López']).save(flush: true, failOnError: true)
            new TestMapJsonb(data: [name: 'Alonso', lastName: 'Torres']).save(flush: true, failOnError: true)
            new TestMapJsonb(data: [name: 'Ernesto', lastName: 'Pérez']).save(flush: true, failOnError: true)

        when:
            def result = pgOrderService.orderByJson()

        then:
            result != null
            result.data.name == ['Iván', 'Ernesto', 'Alonso']
    }

    void 'Order by random'() {
        setup:
            new TestMapJsonb(data: [name: 'Iván', lastName: 'López']).save(flush: true, failOnError: true)
            new TestMapJsonb(data: [name: 'Alonso', lastName: 'Torres']).save(flush: true, failOnError: true)
            new TestMapJsonb(data: [name: 'Ernesto', lastName: 'Pérez']).save(flush: true, failOnError: true)

        when:
            def result = pgOrderService.orderByRandom()

        then:
            result != null
            result.size() == 3
    }
}
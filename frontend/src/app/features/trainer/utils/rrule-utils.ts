import { RRule } from "rrule";

export class RRuleHelper {
  static readonly DAY_MAP: any = {  'Mon': RRule.MO, 'Tue': RRule.TU, 'Wed': RRule.WE, 
  'Thu': RRule.TH, 'Fri': RRule.FR, 'Sat': RRule.SA, 'Sun': RRule.SU };

  static generateRule(freq: any, selectedDays: string[]): string {
    const days = selectedDays.map(d => this.DAY_MAP[d]);
    const rule = new RRule({ freq, byweekday: days });
    return rule.toString().split(':')[1] || rule.toString();
  }
}
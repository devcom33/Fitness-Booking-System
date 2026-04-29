import { RRule, Weekday } from "rrule";



export const DAY_MAP: Record<string, Weekday> = {
    Mon: RRule.MO,
    Tue: RRule.TU,
    Wed: RRule.WE,
    Thu: RRule.TH,
    Fri: RRule.FR,
    Sat: RRule.SA,
    Sun: RRule.SU,
};

export function generateRRuleString(freq: number, selectedDays: string[]): string {
    const options: any = { freq };
  
    if (freq === RRule.WEEKLY) {
        const days = selectedDays
        .map(d => DAY_MAP[d])
        .filter((d): d is Weekday => !!d);
        
        if (days.length > 0) options.byweekday = days;
    }

    return new RRule(options).toString().replace('RRULE:', '');
}

export function toIsoString(dateStr: string): string {
  return dateStr ? new Date(dateStr).toISOString() : '';
}